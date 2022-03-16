package com.example.booking.service

import com.example.booking.model.Restaurant

interface RestaurantService : Service<Restaurant, String> {
    fun getAllRestaurantNames(): MutableList<String>
}