package com.ead.mobileapp

import android.os.Bundle
import com.ead.mobileapp.models.Product
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProductActivity : BackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        enableUpNavigation(toolbar)
        // Extract product data passed via the intent
        val selectedProduct = intent.getSerializableExtra("selectedProduct") as? Product

        // Update the UI with product details
        if (selectedProduct != null) {
            findViewById<TextView>(R.id.product_name).text = selectedProduct.name
            findViewById<TextView>(R.id.product_price).text = selectedProduct.price
            findViewById<TextView>(R.id.product_description).text = selectedProduct.description
        }

        // This sets up the UI elements to adjust for system window insets like status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
