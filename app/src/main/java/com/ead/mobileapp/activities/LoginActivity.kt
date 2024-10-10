package com.ead.mobileapp.activities

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
import com.ead.mobileapp.R
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.dto.auth.LoginRequest
import com.ead.mobileapp.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        val loginRequest = LoginRequest(email, password)
        val authService = RetrofitClient.authService
        lifecycleScope.launch {
            try {
                val loginResponse = authService.login(loginRequest)
                if (loginResponse.isSuccessful && loginResponse.body()?.token != null) {
                    val token = loginResponse.body()?.token!!

                    val sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putBoolean("isAuthenticated", true)
                    editor.putString("token", token)

                    // Decode the JWT token and save claims to SharedPreferences
                    val decodedToken = Utils.decodeJwtToken(token)
                    decodedToken?.let {
                        editor.putString("currentUserEmail", it["email"].toString())
                        editor.putString("currentUserName", it["name"].toString())
                        editor.putString("currentUserPhone", it["mobileNumber"].toString())
                        editor.putString("currentUserRole", it["role"].toString())
                        editor.putString("currentUserAddress", it["address"].toString())
                    }
                    editor.apply()

                    // Navigate to HomeActivity
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // Handle server error response
                    val errorBody = loginResponse.errorBody()?.string()
                    val errorMessage = errorBody?.let { Utils.parseErrorMessage(it) } ?: "Login failed"
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
