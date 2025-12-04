package com.example.vinyl.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyl.R
import com.example.vinyl.adapter.OrderAdapter
import com.example.vinyl.data.database.AppDatabase
import com.example.vinyl.data.repository.OrderRepository
import com.example.vinyl.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    // Репозитории для работы с данными
    private lateinit var userRepository: UserRepository
    private lateinit var orderRepository: OrderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Инициализация базы данных и репозиториев
        val database = AppDatabase.Companion.getDatabase(this)
        userRepository = UserRepository(database)
        orderRepository = OrderRepository(database)

        setupBackButton() // Настройка кнопки "Назад"
        loadUserProfile() // Загрузка данных пользователя
        loadUserOrders()  // Загрузка истории заказов
    }

    // Настройка кнопки "Назад" для возврата на предыдущий экран
    private fun setupBackButton() {
        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // Закрывает текущую Activity
        }
    }

    // Загрузка и отображение данных пользователя
    private fun loadUserProfile() {
        lifecycleScope.launch {
            val user = userRepository.getCurrentUser()

            val tvUsername: TextView = findViewById(R.id.tvUsername)
            val tvEmail: TextView = findViewById(R.id.tvEmail)

            tvUsername.text = "Привет, ${user.username}!"
            tvEmail.text = "Email: ${user.email}"
        }
    }

    // Загрузка и отображение истории заказов пользователя
    private fun loadUserOrders() {
        lifecycleScope.launch {
            val orders = orderRepository.getUserOrders(userId = 1)
            val recyclerView: RecyclerView = findViewById(R.id.rvOrders)

            // Настройка RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this@ProfileActivity)
            recyclerView.adapter = OrderAdapter(orders)
        }
    }
}