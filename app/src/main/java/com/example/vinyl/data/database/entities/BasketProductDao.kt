package com.example.vinyl.data.database

import androidx.room.*
import com.example.vinyl.data.database.entities.BasketProduct

@Dao
interface BasketProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BasketProduct)

    @Query("SELECT * FROM basket_products WHERE basketId = :basketId")
    suspend fun getByBasketId(basketId: Int): List<BasketProduct>

    @Query("SELECT * FROM basket_products WHERE basketId = :basketId AND productId = :productId")
    suspend fun getByProduct(basketId: Int, productId: Int): BasketProduct?

    @Query("UPDATE basket_products SET quantity = :quantity WHERE basketId = :basketId AND productId = :productId")
    suspend fun updateQuantity(basketId: Int, productId: Int, quantity: Int)

    @Query("DELETE FROM basket_products WHERE basketId = :basketId AND productId = :productId")
    suspend fun delete(basketId: Int, productId: Int)
}