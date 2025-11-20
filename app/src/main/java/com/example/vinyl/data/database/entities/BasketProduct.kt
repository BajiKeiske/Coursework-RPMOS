package com.example.vinyl.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_products")
data class BasketProduct(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val basketId: Int,
    val productId: Int,
    val quantity: Int = 1
)