package com.ead.mobileapp.models

import com.ead.mobileapp.dto.order.OrderItem
import java.io.Serializable

data class Order(
    val id: String,
    val items: List<OrderItem>,
    val amount: Float,
    val status: String,
    val createdOn: String,
    val cancellationNotice: String,
    val customerEmail: String,

    ) : Serializable
