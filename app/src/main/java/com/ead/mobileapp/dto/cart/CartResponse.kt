package com.ead.mobileapp.dto.cart

import com.ead.mobileapp.models.CartItem
import java.io.Serializable

data class CartResponse(
    val data: List<CartItem>,
    val message: String,
    val status: String
) : Serializable