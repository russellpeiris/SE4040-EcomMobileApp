package com.ead.mobileapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.R
import com.ead.mobileapp.adapters.OrderDetailAdapter
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.dto.order.OrderItem
import com.ead.mobileapp.repositories.OrderRepository
import kotlinx.coroutines.launch

class OrderDetailsActivity : BackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        enableUpNavigation(toolbar)

        val orderItems = intent.getSerializableExtra("orderItems") as List<OrderItem>

        val orderDetailAdapter = OrderDetailAdapter(orderItems, onCancelRequest = {
            cancelOrder(it.id)
        })
        findViewById<RecyclerView>(R.id.orderDetailsRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@OrderDetailsActivity)
            adapter = orderDetailAdapter
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



