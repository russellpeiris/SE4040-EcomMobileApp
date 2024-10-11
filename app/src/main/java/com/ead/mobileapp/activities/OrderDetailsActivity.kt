package com.ead.mobileapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ead.mobileapp.R
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.models.Order
import com.ead.mobileapp.repositories.OrderRepository
import com.ead.mobileapp.repositories.ProductRepository
import kotlinx.coroutines.launch

class OrderDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        // Setup Toolbar with back navigation
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        // Mock data for now, you can populate these with real order details
        val orderId = findViewById<TextView>(R.id.order_id)
        val orderStatus = findViewById<TextView>(R.id.order_status)
        val orderTracking = findViewById<TextView>(R.id.order_tracking)
        val orderTotal = findViewById<TextView>(R.id.order_total)
        val requestCancelButton = findViewById<Button>(R.id.request_cancel_button)

        // Get order details passed via Intent (if any)
        val order = intent.getSerializableExtra("order") as? Order  // Assuming "Order" is a serializable model

        // If order exists, populate the fields
        if (order != null) {
            orderId.text = "Order ID: #${order.id}"
            orderStatus.text = "Status: ${order.status}"
            orderTracking.text = "Tracking: ${"N/A"}"
            orderTotal.text = "Total: ${order.totalAmount}"
        }

        // Handle order cancellation request
        requestCancelButton.setOnClickListener {
            if (order != null) {
                cancelOrder(order.id)
            }
        }
    }
    private fun cancelOrder(orderId: String) {
        lifecycleScope.launch {
            try {
                val orderRepository = OrderRepository(RetrofitClient.orderService)
                val response = orderRepository.requestCancelOrder(orderId)
                if (response.isSuccessful) {
                    Toast.makeText(this@OrderDetailsActivity, "Order cancellation request sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@OrderDetailsActivity, "Failed to cancel order", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
