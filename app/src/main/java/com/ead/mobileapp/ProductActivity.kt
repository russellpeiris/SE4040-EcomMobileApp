package com.ead.mobileapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ead.mobileapp.models.Product
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)

        // Extract product data passed via the intent
        val selectedProduct = intent.getSerializableExtra("selectedProduct") as? Product

        // Update the UI with product details
        if (selectedProduct != null) {
            findViewById<TextView>(R.id.productTitle).text = selectedProduct.name
            findViewById<TextView>(R.id.productPrice).text = selectedProduct.price
            findViewById<TextView>(R.id.productDescription).text = selectedProduct.description
        }

        // This sets up the UI elements to adjust for system window insets like status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
