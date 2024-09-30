package com.ead.mobileapp.models

import java.io.Serializable

data class Order(
    val orderId: Int,
    val orderNumber: String,
    val orderDate: String,
    val orderTotal: Double,
    val itemsSummary: String
) : Serializable
