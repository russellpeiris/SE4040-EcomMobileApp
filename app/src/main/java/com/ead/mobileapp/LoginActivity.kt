package com.ead.mobileapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ead.mobileapp.api.AuthService
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.dto.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login)

        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty()) {
                emailField.error = "Please enter your email"
                emailField.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordField.error = "Please enter your password"
                passwordField.requestFocus()
                return@setOnClickListener
            }

            performLogin(email, password)
        }

        val registerLink = findViewById<TextView>(R.id.register_link)
        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun performLogin(email: String, password: String) {
//        if (email == "test@test.com" && password == "test123") {
//            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
//
//            val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
//            val editor = sharedPref.edit()
//            editor.putBoolean("isAuthenticated", true)
//            editor.apply()
//
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        } else {
//            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
//        }

        // Create an instance of the login request
        val loginRequest = LoginRequest(email, password)

        // Launch a coroutine to perform the network operation
        val authService = RetrofitClient.authService

        lifecycleScope.launch {
            try {
                // Call the login method from AuthService
                val response = authService.login(loginRequest)
                // Use await() if using Kotlin Coroutines
                if (response.isSuccessful) {
                    // Handle successful response
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()

                        // Store authentication status in SharedPreferences
                        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                        sharedPref.edit().putBoolean("isAuthenticated", true).apply()

                        // Start HomeActivity
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // Handle login failure
                    Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle exceptions (e.g., network errors)
                Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
