package com.ead.mobileapp.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.R
import com.ead.mobileapp.adapters.CartAdapter
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.dto.order.OrderRequest
import com.ead.mobileapp.repositories.CartRepository
import com.ead.mobileapp.repositories.OrderRepository
import kotlinx.coroutines.launch

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

        val checkout = findViewById<TextView>(R.id.checkoutButton)
        checkout.setOnClickListener {
            placeOrder()
        }

        fetchCartItems()
    }

    private fun fetchCartItems() {
        val email = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUserEmail", "") ?: ""

        lifecycleScope.launch {
            try {
                val cartRepository = CartRepository(RetrofitClient.cartService)
                val response = cartRepository.getCartItems(email)
                val cartItems = response?.product
                val totalPrice = response?.totalPrice

                if (cartItems != null && cartItems.isNotEmpty()) {

                    val totalPriceTextView = findViewById<TextView>(R.id.totalPrice)
                    totalPriceTextView.text = "Total: $${totalPrice}"

                    val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this@CartActivity)

                    val cartAdapter = CartAdapter(cartItems) { cartItem ->
                        // Handle remove item logic here
                    }
                    recyclerView.adapter = cartAdapter

                } else {
                    Toast.makeText(this@CartActivity, "Your cart is empty", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@CartActivity, "An error occurred: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun placeOrder() {
        val email = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUserEmail", "") ?: ""

        lifecycleScope.launch {
            try {
                val cartRepository = CartRepository(RetrofitClient.cartService)
                val cartItems = cartRepository.getCartItems(email)?.product
                val orderRequest = OrderRequest(email, cartItems, 0.toString())
                val orderRepository = OrderRepository(RetrofitClient.orderService)
                val response = orderRepository.placeOrder(orderRequest)

                if (response.isSuccessful) {
                    Toast.makeText(this@CartActivity, "Order placed successfully", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@CartActivity, "Failed to place order", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Log.e("CartActivity", "placeOrder: ${e.message}")
                Toast.makeText(this@CartActivity, "An error occurred: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}