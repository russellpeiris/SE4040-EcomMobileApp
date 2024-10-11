package com.ead.mobileapp.dto.cart

import com.ead.mobileapp.models.CartItem
import java.io.Serializable

data class CartResponse(
    val product: List<CartItem>,
    val totalPrice: Float
) : Serializable