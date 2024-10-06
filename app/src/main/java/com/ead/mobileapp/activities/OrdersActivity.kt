package com.ead.mobileapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.R
import com.ead.mobileapp.adapters.OrderAdapter
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.models.Order
import com.ead.mobileapp.repositories.ProductRepository
import kotlinx.coroutines.launch

class OrdersActivity : BackActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        enableUpNavigation(toolbar)

        // Sample orders data
//        val orders = listOf(
//            Order(1, "Order #123456", "01 Jan 2024", 100.00, "Delivered", "123456", 100.00),
//            Order(2, "Order #123457", "02 Jan 2024", 150.00, "Shipped", "123457", 150.00),
//            Order(3, "Order #123458", "03 Jan 2024", 200.00, "Processing", null, 200.00)
//        )

        // RecyclerView setup
//        val recyclerView = findViewById<RecyclerView>(R.id.orderRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        val orderAdapter = OrderAdapter(orders) { order ->
//            // Handle order item click
//            val intent = Intent(this, OrderDetailsActivity::class.java)
//            intent.putExtra("order", order)
//            startActivity(intent)
//        }
//        recyclerView.adapter = orderAdapter

        getOrders();
    }

    private fun getOrders() {

        val productRepository = ProductRepository(RetrofitClient.productService)
        val email = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUserEmail", "") ?: ""


        lifecycleScope.launch {
            try {
                val orders = productRepository.getOrders(email)
                if (orders != null) {
                    // adapter view
                    val recyclerView = findViewById<RecyclerView>(R.id.orderRecyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this@OrdersActivity)
                    val orderAdapter = OrderAdapter(orders) { order ->
                        // Handle order item click
                        val intent = Intent(this@OrdersActivity, OrderDetailsActivity::class.java)
                        intent.putExtra("order", order)
                        startActivity(intent)
                    }
                    recyclerView.adapter = orderAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}