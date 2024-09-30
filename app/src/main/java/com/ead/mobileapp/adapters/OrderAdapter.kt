package com.ead.mobileapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.R
import com.ead.mobileapp.models.Order

class OrderAdapter(private val orderList: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderNumber: TextView = itemView.findViewById(R.id.orderNumber)
        private val orderDate: TextView = itemView.findViewById(R.id.orderDate)
        private val orderTotal: TextView = itemView.findViewById(R.id.orderTotal)
        private val orderItemsSummary: TextView = itemView.findViewById(R.id.orderItemsSummary)

        fun bind(order: Order) {
            orderNumber.text = order.orderNumber
            orderDate.text = "Order Date: ${order.orderDate}"
            orderTotal.text = "Total: $${order.orderTotal}"
            orderItemsSummary.text = "Items: ${order.itemsSummary}"
        }
    }
}
