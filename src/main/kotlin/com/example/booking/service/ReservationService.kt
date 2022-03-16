package com.example.booking.service

import com.example.booking.model.Reservation
import com.example.booking.model.Timeslot
import java.time.LocalDate

interface ReservationService : Service<Reservation, String> {
    fun getAvailableTimeslots(restaurantId: String, date: LocalDate): MutableList<Timeslot>

    fun createReservation(id: String, date: LocalDate, userId: String, restaurantId: String, timeslotId: Int)

    fun deleteReservation(id: String, userId: String)
}