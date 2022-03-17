package com.example.booking.service

import com.example.booking.model.Restaurant
import com.example.booking.repository.RestaurantRepository
import org.springframework.stereotype.Service

@Service
class RestaurantService(private val restaurantRepository: RestaurantRepository) {

    fun add(entity: Restaurant) {
        restaurantRepository.save(entity)
    }

    fun get(): MutableList<Restaurant> {
        return restaurantRepository.findAll()
    }

}