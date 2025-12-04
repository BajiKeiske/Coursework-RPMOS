package com.example.vinyl.data.repository

import com.example.vinyl.data.database.AppDatabase
import com.example.vinyl.data.database.entities.Basket
import com.example.vinyl.data.database.entities.BasketProduct
import com.example.vinyl.data.database.entities.Product
import com.example.vinyl.data.database.entities.Order

class BasketRepository(private val database: AppDatabase) {

    // Получаем корзину пользователя
    suspend fun getBasket(userId: Int): Basket? {
        return database.basketDao().getByUserId(userId)
    }

    // Создаем новую корзину
    suspend fun createBasket(userId: Int): Basket {
        val basket = Basket(id = 0, userId = userId)
        database.basketDao().insert(basket)
        return basket
    }

    // Получаем или создаем корзину
    suspend fun getOrCreateBasket(userId: Int): Basket {
        return getBasket(userId) ?: createBasket(userId)
    }

    // Добавляем товар в корзину (увеличиваем количество если уже есть)
    suspend fun addProductToBasket(userId: Int, productId: Int) {
        val basket = getOrCreateBasket(userId)
        val existing = database.basketProductDao().getByProduct(basket.id, productId)

        if (existing != null) {
            // Увеличиваем количество
            database.basketProductDao().updateQuantity(
                basketId = basket.id,
                productId = productId,
                quantity = existing.quantity + 1
            )
        } else {
            // Добавляем новый товар
            database.basketProductDao().insert(
                BasketProduct(
                    basketId = basket.id,
                    productId = productId,
                    quantity = 1
                )
            )
        }
    }

    // Удаляем товар из корзины (уменьшаем количество или удаляем)
    suspend fun removeProductFromBasket(userId: Int, productId: Int) {
        val basket = getBasket(userId) ?: return
        val existing = database.basketProductDao().getByProduct(basket.id, productId)

        if (existing != null) {
            if (existing.quantity > 1) {
                // Уменьшаем количество
                database.basketProductDao().updateQuantity(
                    basketId = basket.id,
                    productId = productId,
                    quantity = existing.quantity - 1
                )
            } else {
                // Удаляем полностью
                database.basketProductDao().delete(basket.id, productId)
            }
        }
    }

    // Получаем все товары из корзины с количеством
    suspend fun getBasketProducts(userId: Int): List<Pair<Product, Int>> {
        val basket = getBasket(userId) ?: return emptyList()
        val basketProducts = database.basketProductDao().getByBasketId(basket.id)

        return basketProducts.mapNotNull { basketProduct ->
            val product = database.productDao().getById(basketProduct.productId)
            product?.let { Pair(it, basketProduct.quantity) }
        }
    }

    // Оформление заказа
    suspend fun createOrder(userId: Int): Order {
        val basket = getBasket(userId) ?: throw Exception("Корзина пуста")
        val products = getBasketProducts(userId)

        if (products.isEmpty()) throw Exception("Корзина пуста")

        val total = products.sumOf { (product, quantity) -> product.price * quantity }
        val order = Order(
            id = 0,
            userId = userId,
            totalAmount = total,
            status = "COMPLETED"
        )

        database.orderDao().insert(order)
        clearBasket(userId)
        return order
    }

    // Очистка корзины
    suspend fun clearBasket(userId: Int) {
        val basket = getBasket(userId) ?: return
        // Удаляем все товары из корзины
        val basketProducts = database.basketProductDao().getByBasketId(basket.id)
        basketProducts.forEach { basketProduct ->
            database.basketProductDao().delete(basket.id, basketProduct.productId)
        }
    }
}