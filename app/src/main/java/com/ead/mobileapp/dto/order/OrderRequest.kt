package com.ead.mobileapp.dto.order

import com.ead.mobileapp.models.CartItem

data class OrderRequest(
    val customerEmail: String,
    val items: List<CartItem>?,
    val amount: String,
    )
