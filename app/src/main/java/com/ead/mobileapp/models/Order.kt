package com.ead.mobileapp.models

import java.io.Serializable

data class Order(
    val _id: String,
    val product: Product,
    val totalAmount: String,
    val status: String,
    val orderDate: String,

) : Serializable
