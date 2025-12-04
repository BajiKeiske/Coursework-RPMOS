package com.example.vinyl.data.repository

import com.example.vinyl.data.database.AppDatabase
import com.example.vinyl.data.database.entities.Order

class OrderRepository(private val database: AppDatabase) {
    suspend fun getUserOrders(userId: Int): List<Order> {
        return database.orderDao().getByUserId(userId)
    }
}