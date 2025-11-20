package com.example.vinyl.data.database

import androidx.room.*
import com.example.vinyl.data.database.entities.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getById(productId: Int): Product?
}