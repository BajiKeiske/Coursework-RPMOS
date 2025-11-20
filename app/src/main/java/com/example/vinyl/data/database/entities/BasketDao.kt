package com.example.vinyl.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vinyl.data.database.entities.Basket

@Dao
interface BasketDao {
    @Query("SELECT * FROM baskets WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): Basket?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(basket: Basket)
}