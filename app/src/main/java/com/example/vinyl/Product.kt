package com.example.vinyl

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String? = null // опционально для картинок
)