package com.example.booking.service

import com.example.booking.model.User
import com.example.booking.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun add(user: User) {
        userRepository.save(user)
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getById(id: String): User {
        return userRepository.getById(id)
    }

    fun get(): MutableList<User> {
        return userRepository.findAll()
    }

}