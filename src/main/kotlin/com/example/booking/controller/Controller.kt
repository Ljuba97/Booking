package com.example.booking.controller

import com.example.booking.function.getSha256
import com.example.booking.function.isEmailValid
import com.example.booking.model.Reservation
import com.example.booking.model.Restaurant
import com.example.booking.model.Timeslot
import com.example.booking.model.User
import com.example.booking.service.ReservationService
import com.example.booking.service.RestaurantService
import com.example.booking.service.TimeslotService
import com.example.booking.service.UserService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@RestController
class Controller(
    val userService: UserService,
    val restaurantService: RestaurantService,
    val reservationService: ReservationService,
    val timeslotService: TimeslotService
    ) {

    @PostMapping("/api/register")
    fun register(firstName: String, lastName: String, email: String, password: String, phone: String): String {

        val id = UUID.randomUUID().toString()
        val encryptedPassword = getSha256(password)

        for (user in userService.get()) {
            if (user.email == email)
                return "User with this email already exists!"
        }

        if (!isEmailValid(email))
            return "Email not valid!"

        if (password.length < 8)
            return "Password must be at least 8 characters long!"

        userService.add(User(id, firstName, lastName, email, encryptedPassword, phone))
        return "User has successfully registered."
    }

    @PostMapping("/api/login")
    fun login(email: String, password: String): String {

        for (user in userService.get()) {
            if (user.email == email && user.password == getSha256(password))
                return "You have successfully logged in."
        }
        return "Invalid email or password!"
    }

    @PostMapping("/api/restaurant")
    fun addRestaurant(name: String): String {

        val id = UUID.randomUUID().toString()

        if (restaurantService.getAllRestaurantNames().contains(name))
            return "Restaurant with this name already exists!"

        restaurantService.add(Restaurant(id, name))
        return "Restaurant successfully added."
    }

    @GetMapping("/api/restaurants")
    fun showRestaurants() = restaurantService.getAllRestaurantNames()

    @GetMapping("/api/restaurants/{id}")
    fun showRestaurantTimeslots(
        @PathVariable(name = "id") restaurantId: String,
        date: String
    ) = reservationService.getAvailableTimeslots(restaurantId, LocalDate.parse(date))

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

    //test
}