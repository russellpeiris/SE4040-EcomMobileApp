package com.ead.mobileapp.models

import java.io.Serializable

data class Order(
    val id: String,
    val items: List<OrderItem>,
    val amount: String,
    val status: String,
    val createdOn: String,
    val cancellationNotice: String,
    val customerEmail: String,

) : Serializable

data class OrderItem(
    val productId: String,
    val vendorId: String,
    val status: String,
    val price: Double,
    val quantity: Int,
    val id: String
) : Serializable