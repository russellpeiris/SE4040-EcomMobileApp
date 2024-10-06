package com.ead.mobileapp.activities

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

open class BackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Common setup for all activities that inherit from BaseActivity
    }

    // Set up the toolbar back button (up navigation) in a global way
    protected fun enableUpNavigation(toolbar: androidx.appcompat.widget.Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    // Set up a custom back button in a global way
    protected fun setupCustomBackButton(backButton: ImageButton) {
        backButton.setOnClickListener {
            onBackPressed() // Default back button behavior
        }
    }
}
