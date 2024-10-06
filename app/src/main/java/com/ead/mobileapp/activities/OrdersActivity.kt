package com.ead.mobileapp.activities

import android.content.Intent
import android.os.Bundle
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
import com.ead.mobileapp.repositories.OrderRepository
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

        getOrders();
    }

    private fun getOrders() {

        val orderRepository = OrderRepository(RetrofitClient.orderService)
        val email = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUserEmail", "") ?: ""

        lifecycleScope.launch {
            try {
                val orders = orderRepository.getOrders(email)
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