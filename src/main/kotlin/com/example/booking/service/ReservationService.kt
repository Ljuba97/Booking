package com.example.booking.service

import com.example.booking.model.Reservation
import com.example.booking.model.Timeslot
import com.example.booking.repository.ReservationRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservationService(private val reservationRepository: ReservationRepository) {

    fun getAvailableTimeslots(restaurantId: String, date: LocalDate): MutableList<Timeslot> {
        return reservationRepository.getAvailableTimeslots(restaurantId, date)
    }

    fun createReservation(id: String, date: LocalDate, userId: String, restaurantId: String, timeslotId: Int) {
        reservationRepository.createReservation(id, date, userId, restaurantId, timeslotId)
    }

    fun deleteReservation(id: String, userId: String) {
        reservationRepository.deleteReservation(id, userId)
    }
}