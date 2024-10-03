package com.ead.mobileapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.ead.mobileapp.models.Order

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
            orderId.text = "Order ID: #${order.orderId}"
            orderStatus.text = "Status: ${order.orderStatus}"
            orderTracking.text = "Tracking: ${order.trackingNumber ?: "N/A"}"
            orderTotal.text = "Total: $${order.total}"
        }

        // Handle order cancellation request
        requestCancelButton.setOnClickListener {
            // Logic to request order cancellation, could be a network call, etc.
            Toast.makeText(this, "Cancellation request sent for Order #${order?.orderId}", Toast.LENGTH_SHORT).show()
        }
    }
}
