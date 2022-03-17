package com.example.booking.service

import com.example.booking.model.Restaurant
import com.example.booking.repository.RestaurantRepository
import org.springframework.stereotype.Service

@Service
class RestaurantServiceImpl(val restaurantRepository: RestaurantRepository) : RestaurantService {

    override fun add(entity: Restaurant) {
        restaurantRepository.save(entity)
    }

    override fun get(): MutableList<Restaurant> {
        return restaurantRepository.findAll()
    }

}