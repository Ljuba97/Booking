package com.example.booking.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "restaurant")
@Entity
class Restaurant(
    @Id
    @Column(name = "id", nullable = false)
    var id: String = UUID.randomUUID().toString(),

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "address", unique = true, nullable = false)
    var address: String,

    @Column(name = "phone", nullable = false)
    var phone: String
)