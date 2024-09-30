package com.ead.mobileapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.adapters.OrderAdapter
import com.ead.mobileapp.models.Order

class OrderActivity : BackActivity() {
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
        val orders = listOf(
            Order(1, "Order #123456", "01 Jan 2024", 100.00, "2x Product A, 1x Product B"),
            Order(2, "Order #123457", "02 Jan 2024", 150.00, "3x Product C, 2x Product D"),
            Order(3, "Order #123458", "03 Jan 2024", 200.00, "1x Product E, 1x Product F")
        )

        // RecyclerView setup
        val recyclerView = findViewById<RecyclerView>(R.id.orderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val orderAdapter = OrderAdapter(orders)
        recyclerView.adapter = orderAdapter
    }
}