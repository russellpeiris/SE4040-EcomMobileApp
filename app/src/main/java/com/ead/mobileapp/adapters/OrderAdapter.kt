package com.ead.mobileapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ead.mobileapp.R
import com.ead.mobileapp.models.Order

class OrderAdapter(
    private val orderList: List<Order>,
    private val onOrderClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.bind(order)
        holder.itemView.setOnClickListener { onOrderClick(order) } // Set the click listener
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderNumber: TextView = itemView.findViewById(R.id.orderNumber)
        private val orderDate: TextView = itemView.findViewById(R.id.orderDate)
        private val orderTotal: TextView = itemView.findViewById(R.id.orderTotal)
        private val orderStatus: TextView = itemView.findViewById(R.id.orderStatus)

        fun bind(order: Order) {
            orderNumber.text = "Order Number: ${order._id.substring(0, 4)}"
            orderDate.text = "Order Date: ${order.orderDate}"
            orderTotal.text = "Total: $${order.totalAmount}"
            orderStatus.text = "Status: ${order.status}"
            Glide.with(itemView)
                .load(order.product.imageUrl)
                .into(itemView.findViewById<ImageView>(R.id.orderImage))
        }
    }
}

