package com.example.booking.service

import com.example.booking.model.Timeslot
import java.time.LocalTime

interface TimeslotService : Service<Timeslot, Int> {
    fun getTimeslotId(startTime: LocalTime, endTime: LocalTime): Int
}