package com.example.booking.service

import com.example.booking.model.Timeslot
import com.example.booking.repository.TimeslotRepository
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class TimeslotServiceImpl(val timeslotRepository: TimeslotRepository) : TimeslotService {
    override fun getTimeslotId(startTime: LocalTime, endTime: LocalTime): Int {
        return timeslotRepository.getTimeslotId(startTime, endTime)
    }

    override fun add(entity: Timeslot) {
        TODO("Not yet implemented")
    }

    override fun get(): MutableList<Timeslot> {
        TODO("Not yet implemented")
    }
}