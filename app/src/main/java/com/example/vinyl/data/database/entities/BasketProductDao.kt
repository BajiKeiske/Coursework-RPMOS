package com.example.vinyl.data.database

import androidx.room.*
import com.example.vinyl.data.database.entities.BasketProduct

@Dao
interface BasketProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BasketProduct)

    @Query("SELECT * FROM basket_products WHERE basketId = :basketId")
    suspend fun getByBasketId(basketId: Int): List<BasketProduct>
    @Query("DELETE FROM basket_products WHERE basketId = :basketId AND productId = :productId")
    suspend fun deleteByProductId(basketId: Int, productId: Int)
}