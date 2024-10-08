package com.ead.mobileapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.ead.mobileapp.R

class ProfileActivity : BackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars =
                insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Toolbar with back navigation
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        enableUpNavigation(toolbar)

        // Initialize UI Elements
        val emailTextView = findViewById<TextView>(R.id.email)
        val nameEditText = findViewById<EditText>(R.id.name)
        val phoneEditText = findViewById<EditText>(R.id.phone)
        val addressEditText = findViewById<EditText>(R.id.address)
        val updateButton = findViewById<Button>(R.id.updateButton)
        val logoutButton = findViewById<Button>(R.id.logout)
        val deactivateButton = findViewById<Button>(R.id.deactivate)
        Glide.with(this)
            .load("https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50")
            .into(findViewById(R.id.profilePicture))

        // Retrieve saved data from SharedPreferences
        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val savedName = sharedPref.getString("currentUserName", "")
        val savedPhone = sharedPref.getString("currentUserPhone", "")
        val savedAddress = sharedPref.getString("currentUserAddress", "")
        val savedEmail = sharedPref.getString("currentUserEmail", "")

        // Set initial values
        nameEditText.setText(savedName)
        phoneEditText.setText(savedPhone)
        addressEditText.setText(savedAddress)
        emailTextView.text = savedEmail

        // Handle Update Button click
        updateButton.setOnClickListener {
            val newName = nameEditText.text.toString().trim()
            val newPhone = phoneEditText.text.toString().trim()
            val newAddress = addressEditText.text.toString().trim()

            // Save updated data to SharedPreferences
            with(sharedPref.edit()) {
                putString("name", newName)
                putString("phone", newPhone)
                putString("address", newAddress)
                apply()
            }

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
        }

        // Handle Logout Button click
        logoutButton.setOnClickListener {
            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show()
            with(sharedPref.edit()) {
                putBoolean("isAuthenticated", false)
                apply()
            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}