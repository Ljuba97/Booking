package com.example.booking.repository

import com.example.booking.model.Restaurant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository : JpaRepository<Restaurant, String> {
    @Query("SELECT name FROM Restaurant")
    fun getAllRestaurantNames(): MutableList<String>
}