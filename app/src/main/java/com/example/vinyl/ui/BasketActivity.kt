package com.example.vinyl.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyl.R
import com.example.vinyl.adapter.BasketAdapter
import com.example.vinyl.data.database.AppDatabase
import com.example.vinyl.data.repository.BasketRepository
import kotlinx.coroutines.launch

class BasketActivity : AppCompatActivity() {
    private lateinit var basketRepository: BasketRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmptyBasket: TextView
    private lateinit var btnCheckout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        val database = AppDatabase.getDatabase(this)
        basketRepository = BasketRepository(database)

        setupUI() // здесь btnCheckout инициализируется
        setupCheckoutButton() // теперь кнопка готова
        loadBasketProducts()
    }
    //находит все элементы экрана и сохраняет в переменные
    private fun setupUI() {
        val btnBack: Button = findViewById(R.id.btnBack)
        tvEmptyBasket = findViewById(R.id.tvEmptyBasket)
        recyclerView = findViewById(R.id.rvBasketItems)
        btnCheckout = findViewById(R.id.btnCheckout) // ИНИЦИАЛИЗАЦИЯ ЗДЕСЬ

        recyclerView.layoutManager = LinearLayoutManager(this)

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadBasketProducts() {
        lifecycleScope.launch {
            try {
                val products = basketRepository.getBasketProducts(userId = 1)

                if (products.isEmpty()) {
                    tvEmptyBasket.visibility = TextView.VISIBLE
                    recyclerView.visibility = TextView.GONE
                    btnCheckout.visibility = Button.GONE
                } else {
                    tvEmptyBasket.visibility = TextView.GONE
                    recyclerView.visibility = TextView.VISIBLE
                    btnCheckout.visibility = Button.VISIBLE
                    recyclerView.adapter = BasketAdapter(products) { product ->
                        lifecycleScope.launch {
                            basketRepository.removeProductFromBasket(userId = 1, productId = product.id)
                            loadBasketProducts()
                            Toast.makeText(this@BasketActivity, "Удалено: ${product.name}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@BasketActivity, "Ошибка загрузки корзины", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // Настройка кнопки оформления заказа
    private fun setupCheckoutButton() {
        val btnCheckout: Button = findViewById(R.id.btnCheckout)

        btnCheckout.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val order = basketRepository.createOrder(userId = 1)
                    basketRepository.clearBasket(userId = 1)

                    Toast.makeText(
                        this@BasketActivity,
                        "Заказ оформлен! Сумма: ${order.totalAmount} руб",
                        Toast.LENGTH_LONG
                    ).show()

                    loadBasketProducts() // обновляем список
                } catch (e: Exception) {
                    Toast.makeText(
                        this@BasketActivity,
                        "Ошибка: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}