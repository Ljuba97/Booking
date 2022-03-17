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
    var id: Int,

    @Column(name = "start_time", nullable = false)
    var startTime: LocalTime,

    @Column(name = "end_time", nullable = false)
    var endTime: LocalTime
)