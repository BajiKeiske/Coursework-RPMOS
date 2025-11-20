package com.example.vinyl.ui.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        val database = AppDatabase.getDatabase(this)
        basketRepository = BasketRepository(database)

        setupUI()
        loadBasketProducts()
    }

    private fun setupUI() {
        val btnBack: Button = findViewById(R.id.btnBack)
        tvEmptyBasket = findViewById(R.id.tvEmptyBasket)
        recyclerView = findViewById(R.id.rvBasketItems)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadBasketProducts() {
        lifecycleScope.launch {
            val products = basketRepository.getBasketProducts(userId = 1)

            if (products.isEmpty()) {
                tvEmptyBasket.visibility = TextView.VISIBLE
                recyclerView.visibility = TextView.GONE
            } else {
                tvEmptyBasket.visibility = TextView.GONE
                recyclerView.visibility = TextView.VISIBLE
                recyclerView.adapter = BasketAdapter(products)
            }
        }
    }
}