package com.example.vinyl.data.repository

import com.example.vinyl.data.database.AppDatabase
import com.example.vinyl.data.database.entities.Basket
import com.example.vinyl.data.database.entities.BasketProduct
import com.example.vinyl.data.database.entities.Product

class BasketRepository(private val database: AppDatabase) {

    suspend fun getBasket(userId: Int): Basket? {
        return database.basketDao().getByUserId(userId)
    }

    suspend fun createBasket(userId: Int): Basket {
        val basket = Basket(id = 0, userId = userId)
        database.basketDao().insert(basket)
        return basket
    }

    suspend fun getOrCreateBasket(userId: Int): Basket {
        return getBasket(userId) ?: createBasket(userId)
    }

    suspend fun addProductToBasket(userId: Int, productId: Int) {
        val basket = getOrCreateBasket(userId)
        val basketProduct = BasketProduct(
            basketId = basket.id,
            productId = productId,
            quantity = 1
        )
        database.basketProductDao().insert(basketProduct)
    }

    // Новый метод: получаем все товары из корзины пользователя
    suspend fun getBasketProducts(userId: Int): List<Product> {
        val basket = getBasket(userId) ?: return emptyList()
        val basketProducts = database.basketProductDao().getByBasketId(basket.id)

        return basketProducts.map { basketProduct ->
            database.productDao().getById(basketProduct.productId)
        }.filterNotNull()
    }

    suspend fun removeProductFromBasket(userId: Int, productId: Int) {
        val basket = getBasket(userId) ?: return
        database.basketProductDao().deleteByProductId(basket.id, productId)
    }
}