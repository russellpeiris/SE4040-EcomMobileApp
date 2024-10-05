package com.ead.mobileapp.dto.cart

import com.ead.mobileapp.models.Product
import java.io.Serializable

data class AddToCartRequest (
    val email: String,
    val product: Product
) : Serializable