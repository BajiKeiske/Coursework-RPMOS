package com.example.vinyl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyl.R
import com.example.vinyl.adapter.ProductAdapter
import com.example.vinyl.data.database.AppDatabase
import com.example.vinyl.data.repository.BasketRepository
import com.example.vinyl.data.repository.ProductRepository
import com.example.vinyl.ui.BasketActivity
import com.example.vinyl.ui.ProfileActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var productRepository: ProductRepository
    private lateinit var basketRepository: BasketRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = AppDatabase.getDatabase(this)
        productRepository = ProductRepository(database)
        basketRepository = BasketRepository(database)

        setupRecyclerView()
        setupCartButton()
        setupProfileButton()
    }
    //переход на профиль
    private fun setupProfileButton() {
        val btnProfile: Button = findViewById(R.id.btnProfile)
        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.rvProducts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            productRepository.initializeSampleData()
            val products = productRepository.getAllProducts()

            recyclerView.adapter = ProductAdapter(products) { product ->
                lifecycleScope.launch {
                    basketRepository.addProductToBasket(userId = 1, productId = product.id)
                    Toast.makeText(this@MainActivity, "Добавлено в корзину: ${product.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupCartButton() {
        val btnCart: Button = findViewById(R.id.btnCart)
        btnCart.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }
    }
}