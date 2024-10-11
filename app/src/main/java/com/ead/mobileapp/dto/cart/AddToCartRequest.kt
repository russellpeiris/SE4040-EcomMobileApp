package com.ead.mobileapp.dto.cart

import com.ead.mobileapp.models.Product
import java.io.Serializable

data class AddToCartRequest (
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String,
    val description: String,
    val category: String,
    val vendorId: String,
    val isActivated: Boolean
) : Serializable