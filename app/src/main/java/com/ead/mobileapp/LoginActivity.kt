package com.ead.mobileapp

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
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.dto.auth.LoginRequest
import com.ead.mobileapp.utils.Utils
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
        val loginRequest = LoginRequest(email, password)
        val authService = RetrofitClient.authService
        lifecycleScope.launch {

            try {
                val loginResponse = authService.login(loginRequest)
                if(loginResponse.isSuccessful){

                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                    val sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putBoolean("isAuthenticated", true)
//                  editor.putString("token", response.data?.token) // Uncomment if token exists in response
                    editor.apply()
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {

                    val errorBody = loginResponse.errorBody()?.string()
                    val errorMessage = errorBody?.let {  Utils.parseErrorMessage(it) }
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                   Toast.makeText(this@LoginActivity, "An error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }



}

