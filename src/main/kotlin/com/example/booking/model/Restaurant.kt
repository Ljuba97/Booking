package com.example.booking.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "restaurant")
@Entity
class Restaurant(
    @Id
    @Column(name = "id", nullable = false)
    val id: String,

    @Column(name = "name", nullable = false)
    val name: String
)