package com.ead.mobileapp.dto.order

import java.io.Serializable


data class OrderItem(
    val productId: String,
    val vendorId: String,
    val status: String,
    val price: Double,
    val quantity: Int,
    val id: String
) : Serializable