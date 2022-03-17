package com.example.booking.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "restaurant")
@Entity
class Restaurant {
    @Id
    @Column(name = "id", nullable = false)
    var id = ""

    @Column(name = "name", nullable = false)
    var name = ""

    @Column(name = "address", unique = true, nullable = false)
    var address = ""

    @Column(name = "phone", nullable = false)
    var phone = ""
}