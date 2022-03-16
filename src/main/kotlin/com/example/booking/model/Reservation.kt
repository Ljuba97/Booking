package com.example.booking.model

import java.time.LocalDate
import javax.persistence.*

@Table(
    name = "reservation", indexes = [
        Index(name = "restaurant_id_idx", columnList = "restaurant_id"),
        Index(name = "timeslot_id_idx", columnList = "timeslot_id"),
        Index(name = "user_id_idx", columnList = "user_id")
    ]
)
@Entity
class Reservation(
    @Id
    @Column(name = "id", nullable = false)
    var id: String,

    @Column(name = "date", nullable = false)
    var date: LocalDate,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    var restaurant: Restaurant,

    @ManyToOne(optional = false)
    @JoinColumn(name = "timeslot_id", nullable = false)
    var timeslot: Timeslot
)