package com.example.booking.repository

import com.example.booking.model.Timeslot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalTime

@Repository
interface TimeslotRepository : JpaRepository<Timeslot, Int> {
    @Query("SELECT id FROM Timeslot WHERE startTime = :startTime AND endTime = :endTime")
    fun getTimeslotId(@Param("startTime") startTime: LocalTime, @Param("endTime") endTime: LocalTime): Int
}