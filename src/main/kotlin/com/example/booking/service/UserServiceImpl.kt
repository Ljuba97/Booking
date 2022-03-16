package com.example.booking.service

import com.example.booking.model.User
import com.example.booking.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepository: UserRepository) : UserService {

    override fun add(entity: User) {
        userRepository.save(entity)
    }

    override fun get(): MutableList<User> {
        return userRepository.findAll()
    }

}