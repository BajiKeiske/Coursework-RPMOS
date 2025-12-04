package com.example.vinyl.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // автоинкремент
    val userId: Int,
    val totalAmount: Double,
    val status: String
)