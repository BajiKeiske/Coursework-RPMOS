package com.example.vinyl.data.repository

import com.example.vinyl.data.database.AppDatabase
import com.example.vinyl.data.database.entities.Product

class ProductRepository(private val database: AppDatabase) {

    suspend fun getAllProducts(): List<Product> {
        return database.productDao().getAll()
    }

    suspend fun insertProducts(products: List<Product>) {
        database.productDao().insertAll(products)
    }

    // Добавляем тестовые данные при первом запуске
    suspend fun initializeSampleData() {
        if (database.productDao().getAll().isEmpty()) {
            val sampleProducts = listOf(
                Product(1, "Электрогитара Fender Stratocaster", 45000.0),
                Product(2, "Акустическая гитара Yamaha", 25000.0),
                Product(3, "Барабанная установка Pearl", 120000.0),
                Product(4, "Синтезатор Korg", 65000.0),
                Product(5, "Саксофон Yamaha", 55000.0)
            )
            insertProducts(sampleProducts)
        }
    }
}