package com.ead.mobileapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.adapters.CartAdapter
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.models.CartItem
import com.ead.mobileapp.repositories.ProductRepository
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

//        val cartItems = listOf(
//            CartItem("Product 1", 10.99, 1),
//            CartItem("Product 2", 20.50, 2),
//            CartItem("Product 3", 5.00, 1)
//        )

//        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        val cartAdapter = CartAdapter(cartItems) { cartItem ->
//            // Handle remove item logic here
//        }
//        recyclerView.adapter = cartAdapter

            fetchCartItems()
    }

    private fun fetchCartItems() {
        val email = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUser", "") ?: ""

        lifecycleScope.launch {
            try {
                // Get the productService and repository
                val productService = RetrofitClient.productService
                val productRepository = ProductRepository(productService)

                // Fetch cart items from the repository
                val response = productRepository.getCartItems(email)

                // Check if the response is successful
                if (response.isSuccessful) {
                    val cartItems = response.body()?.data
                    if (cartItems != null && cartItems.isNotEmpty()) {
                        // Set up RecyclerView with the fetched cart items
                        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(this@CartActivity)

                        val cartAdapter = CartAdapter(cartItems) { cartItem ->
                            // Handle remove item logic here
                        }
                        recyclerView.adapter = cartAdapter
                    } else {
                        // If the cart is empty, show a message or handle accordingly
                        Toast.makeText(this@CartActivity, "Your cart is empty", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle response failure (e.g., server error)
                    Toast.makeText(this@CartActivity, "Failed to fetch cart items", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle any errors that occur during the API call
                Toast.makeText(this@CartActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}