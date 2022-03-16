package com.example.booking.model

import java.time.LocalTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "timeslot")
@Entity
class Timeslot(
    @Id
    @Column(name = "id", nullable = false)
    val id: Int,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(name = "end_time", nullable = false)
    val endTime: LocalTime
)