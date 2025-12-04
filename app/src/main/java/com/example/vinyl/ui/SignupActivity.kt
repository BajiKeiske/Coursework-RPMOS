package com.example.vinyl.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vinyl.MainActivity
import com.example.vinyl.R
import com.example.vinyl.data.database.AppDatabase
import com.example.vinyl.data.repository.UserRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val database = AppDatabase.getDatabase(this)
        userRepository = UserRepository(database)

        setupSignupButton()
        setupLoginLink()
    }

    private fun setupSignupButton() {
        val btnSignup = findViewById<MaterialButton>(R.id.btnSignup)

        btnSignup.setOnClickListener {
            val etName = findViewById<TextInputEditText>(R.id.etName)
            val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
            val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
            val etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)

            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    // TODO: Реальная регистрация
                    Toast.makeText(this@SignupActivity, "Регистрация успешна!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(this@SignupActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupLoginLink() {
        val tvLoginLink = findViewById<android.widget.TextView>(R.id.tvLoginLink)

        tvLoginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}