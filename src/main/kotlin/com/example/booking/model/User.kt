package com.example.booking.model

import com.example.booking.function.getSha256
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "user")
@Entity
class User {
    @Id
    @Column(name = "id", nullable = false)
    var id = ""

    @Column(name = "first_name", nullable = false)
    var firstName = ""

    @Column(name = "last_name", nullable = false)
    var lastName = ""

    @Column(name = "email", unique = true, nullable = false)
    var email = ""

    @Column(name = "password", nullable = false)
    var password = ""
        @JsonIgnore
        get

    @Column(name = "phone")
    var phone = ""

    fun comparePassword(password: String): Boolean {
        return this.password == getSha256(password)
    }
}