package com.ead.mobileapp.models

import java.io.Serializable

data class Order(
    val id: String,
    val items: List<CartItem>,
    val amount: String,
    val status: String,
    val createdOn: String,
    val cancellationNotice: String,
    val customerEmail: String,

) : Serializable
