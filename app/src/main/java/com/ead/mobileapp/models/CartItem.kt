package com.ead.mobileapp.models

import java.io.Serializable

data class CartItem(
    val name: String,
    val imageUrl: String,
    val price: String,
    val quantity: Int,
    val category: String,
) : Serializable
