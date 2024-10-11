package com.ead.mobileapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.R
import com.ead.mobileapp.models.OrderItem

class OrderDetailAdapter(
    private val orders: List<OrderItem>,
    private val onCancelRequest: (OrderItem) -> Unit
) : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_item, parent, false)
        return OrderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)

        // Handle the "Request Order Cancellation" button click
        holder.cancelButton.setOnClickListener {
            onCancelRequest(order)
        }
    }

    override fun getItemCount(): Int = orders.size

    inner class OrderDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderId: TextView = itemView.findViewById(R.id.order_id)
        private val orderStatus: TextView = itemView.findViewById(R.id.order_status)
        private val orderTracking: TextView = itemView.findViewById(R.id.order_tracking)
        private val orderTotal: TextView = itemView.findViewById(R.id.order_total)
        val cancelButton: Button = itemView.findViewById(R.id.request_cancel_button)

        fun bind(order: OrderItem) {
            orderId.text = "Order ID: #${order.id}"
            orderStatus.text = "Status: ${order.status}"
//            orderTracking.text = "Tracking: ${order.trackingId ?: "Not available"}"
            orderTotal.text = "Total: $${order.price}"
        }
    }
}
