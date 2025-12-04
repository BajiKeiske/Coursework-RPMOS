package com.example.vinyl.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val role: String = "USER"
)