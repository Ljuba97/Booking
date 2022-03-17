package com.example.booking.controller

import com.example.booking.dto.RestaurantDTO
import com.example.booking.dto.LoginDTO
import com.example.booking.dto.Message
import com.example.booking.dto.RegisterDTO
import com.example.booking.function.getSha256
import com.example.booking.function.isEmailValid
import com.example.booking.model.Restaurant
import com.example.booking.model.User
import com.example.booking.service.ReservationService
import com.example.booking.service.RestaurantService
import com.example.booking.service.TimeslotService
import com.example.booking.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
class Controller(
    val userService: UserService,
    val restaurantService: RestaurantService,
    val reservationService: ReservationService,
    val timeslotService: TimeslotService
    ) {

    @PostMapping("/api/register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<Any> {
        val user = User()
        user.id = body.id
        user.firstName = body.firstName
        user.lastName = body.lastName
        user.email = body.email
        user.password = getSha256(body.password)
        user.phone = body.phone

        for (el in userService.get()) {
            if (el.email == user.email)
                return ResponseEntity.status(409).body(Message("user with this email already exists!"))
        }

        if (!isEmailValid(user.email))
            return ResponseEntity.badRequest().body(Message("invalid email!"))

        if (body.password.length < 8)
            return ResponseEntity.badRequest().body(Message("password must be at least 8 characters long!"))

        return ResponseEntity.ok(userService.add(user))
    }

    @PostMapping("/api/login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(Message("user not found!"))

        if (!user.comparePassword(body.password))
            return ResponseEntity.badRequest().body(Message("invalid password!"))

        val issuer = user.id

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //day
            .signWith(SignatureAlgorithm.HS256, "secret").compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseEntity.ok(Message("success"))
    }

    @GetMapping("/api/user")
    fun user(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        try {
            if (jwt == null)
                return ResponseEntity.status(401).body(Message("unauthenticated"))

            val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body

            return ResponseEntity.ok(userService.getById(body.issuer))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }
    }

    @PostMapping("/api/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie("jwt", "")

        cookie.maxAge = 0
        response.addCookie(cookie)

        return ResponseEntity.ok(Message("success"))
    }

    @PostMapping("/api/restaurant")
    fun addRestaurant(@RequestBody body: RestaurantDTO): ResponseEntity<Any> {
        val restaurant = Restaurant()
        restaurant.id = body.id
        restaurant.name = body.name
        restaurant.address = body.address
        restaurant.phone = body.phone

        for (el in restaurantService.get()) {
            if (el.address == restaurant.address)
                return ResponseEntity.status(409).body(Message("restaurant with this address already exists!"))
        }

        return ResponseEntity.ok(restaurantService.add(restaurant))
    }

    @GetMapping("/api/restaurants")
    fun showRestaurants(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        if (jwt == null)
            return ResponseEntity.status(401).body(Message("unauthenticated"))

        return ResponseEntity.ok(restaurantService.get())
    }

    @GetMapping("/api/restaurants/{id}")
    fun showRestaurantTimeslots(
        @CookieValue("jwt") jwt: String?,
        @PathVariable(name = "id") restaurantId: String,
        date: String
    ): ResponseEntity<Any> {
        if (jwt == null)
            return ResponseEntity.status(401).body(Message("unauthenticated"))

        val availableTime: MutableList<String> = mutableListOf()

        for (timeslot in reservationService.getAvailableTimeslots(restaurantId, LocalDate.parse(date))) {
            availableTime.add(timeslot.startTime.toString() + " - " + timeslot.endTime.toString())
        }

        return ResponseEntity.ok(availableTime)
    }

    //Unfinished!

    @PostMapping("/api/create-reservation")
    fun createReservation(
        @RequestHeader(name = "user") userId: String,
        @RequestHeader(name = "restaurant") restaurantId: String,
        date: String,
        startTime: String,
        endTime: String
    ) {
        val id = UUID.randomUUID().toString()
        val timeslotId = timeslotService.getTimeslotId(LocalTime.parse(startTime), LocalTime.parse(endTime))

        reservationService.createReservation(id, LocalDate.parse(date), userId, restaurantId, timeslotId)
    }

    @PostMapping("/api/cancel-reservation")
    fun cancelReservation(
        @RequestHeader(name = "reservation") reservationId: String,
        @RequestHeader(name = "user") userId: String
    ) {
        reservationService.deleteReservation(reservationId, userId)
    }
}