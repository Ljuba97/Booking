package com.example.booking.dto

import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class CreateReservationDTO {
    var id = UUID.randomUUID().toString()
    var date: LocalDate = LocalDate.now()
    var startTime: LocalTime = LocalTime.now()
    var endTime: LocalTime = LocalTime.now()
    var restaurantId = ""
}