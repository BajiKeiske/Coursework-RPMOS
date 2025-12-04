package com.example.vinyl.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "basket_products",
    primaryKeys = ["basketId", "productId"] // составной первичный ключ
)
data class BasketProduct(
    val basketId: Int,
    val productId: Int,
    val quantity: Int = 1
)