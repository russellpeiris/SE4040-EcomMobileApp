package com.ead.mobileapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ead.mobileapp.R
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.dto.auth.RegisterRequest
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameField = findViewById<EditText>(R.id.name)
        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)
        val confirmPasswordField = findViewById<EditText>(R.id.confirm_password)
        val registerButton = findViewById<Button>(R.id.register)

        registerButton.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            register(name, email, password)
        }

        val loginLink = findViewById<TextView>(R.id.login_link)
        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun register(
        name: String,
        email: String,
        password: String,
    ){
        val registerRequest = RegisterRequest(name, email, password)
        val authService = RetrofitClient.authService

        lifecycleScope.launch {

            try {
                val registerResponse = authService.register(registerRequest)

                if (registerResponse.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Success: Await Account Activation", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "An error occurred", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
