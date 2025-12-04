package com.example.vinyl.data.database.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: Order)

    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY id DESC")
    suspend fun getByUserId(userId: Int): List<Order>
}