package com.ead.mobileapp.models

import java.io.Serializable

data class Order(
    val _id: String,
    val product: Product,
    val totalAmount: String,
    val status: String,
    val orderDate: String,

) : Serializable


data class OrderRequest(
    val orderNumber: String,
    val orderDate: String,
    val orderTotal: Double,
    val orderStatus: String,
    val trackingNumber: String?,
    val total: Double,
    val items: List<OrderItemRequest>
)

data class OrderItemRequest(
    val productId: String,
    val quantity: Int,
    val price: Double
)

data class OrderUpdateRequest(
    val orderStatus: String,
    val trackingNumber: String?,
    val total: Double,
    val items: List<OrderItemRequest>
)
