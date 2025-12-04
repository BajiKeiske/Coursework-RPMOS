package com.example.vinyl.data.repository

import com.example.vinyl.data.database.AppDatabase
import com.example.vinyl.data.database.entities.User

class UserRepository(private val database: AppDatabase) {
    suspend fun getCurrentUser(): User {
        // Пока заглушка - юзер с id=1
        return User(
            id = 1,
            username = "music_lover",
            email = "user@example.com",
            password = "123",
            role = "USER"
        )
    }
}