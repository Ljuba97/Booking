package com.example.booking.service

import com.example.booking.model.Timeslot
import com.example.booking.repository.TimeslotRepository
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class TimeslotService(private val timeslotRepository: TimeslotRepository) {

    fun getTimeslotId(startTime: LocalTime, endTime: LocalTime): Int {
        return timeslotRepository.getTimeslotId(startTime, endTime)
    }
}