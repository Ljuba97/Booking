package com.example.booking.repository

import com.example.booking.model.Reservation
import com.example.booking.model.Timeslot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import javax.transaction.Transactional

@Repository
interface ReservationRepository : JpaRepository<Reservation, String> {
    @Query("SELECT t FROM Reservation r RIGHT JOIN Timeslot t " +
            "ON r.timeslot = t.id WHERE t.startTime NOT IN (" +
            "SELECT t.startTime FROM Reservation r INNER JOIN Timeslot t " +
            "ON r.timeslot.id = t.id WHERE r.restaurant.id = :restaurantId AND r.date = :date) " +
            "AND t.endTime NOT IN (SELECT t.endTime FROM Reservation r INNER JOIN Timeslot t " +
            "ON r.timeslot.id = t.id WHERE r.restaurant.id = :restaurantId AND r.date = :date)")
    fun getAvailableTimeslots(
        @Param("restaurantId") restaurantId: String,
        @Param("date") date: LocalDate
    ): MutableList<Timeslot>

    @Modifying
    @Transactional
    @Query("INSERT INTO Reservation VALUES (:id, :date, :userId, :restaurantId, :timeslotId)", nativeQuery = true)
    fun createReservation(
        @Param("id") id: String,
        @Param("date") date: LocalDate,
        @Param("userId") userId: String,
        @Param("restaurantId") restaurantId: String,
        @Param("timeslotId") timeslotId: Int
    )

    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation r WHERE r.id = :id AND r.user.id = :userId")
    fun deleteReservation(@Param("id") id: String, @Param("userId") userId: String)
}