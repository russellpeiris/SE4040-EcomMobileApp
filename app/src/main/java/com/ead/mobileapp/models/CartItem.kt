package com.ead.mobileapp.models

import java.io.Serializable

data class CartItem(val name: String, val price: String, val quantity: Int) : Serializable

data class CartItemResponse(
    val data: List<CartItem>,
    val message: String,
    val status: String
) : Serializable