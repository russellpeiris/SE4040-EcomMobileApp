package com.ead.mobileapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.adapters.CartAdapter
import com.ead.mobileapp.models.CartItem

class CartActivity : BackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        enableUpNavigation(toolbar)

        val cartItems = listOf(
            CartItem("Product 1", 10.99, 1),
            CartItem("Product 2", 20.50, 2),
            CartItem("Product 3", 5.00, 1)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cartAdapter = CartAdapter(cartItems) { cartItem ->
            // Handle remove item logic here
        }
        recyclerView.adapter = cartAdapter
    }
}