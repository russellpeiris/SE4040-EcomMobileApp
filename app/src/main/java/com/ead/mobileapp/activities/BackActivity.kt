package com.ead.mobileapp.activities

import androidx.appcompat.app.AppCompatActivity

open class BackActivity : AppCompatActivity() {

    protected fun enableUpNavigation(toolbar: androidx.appcompat.widget.Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
