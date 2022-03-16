package com.example.booking.service

interface Service<T, ID> {

    fun add(entity: T)

    fun get(): MutableList<T>
}