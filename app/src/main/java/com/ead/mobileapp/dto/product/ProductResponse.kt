package com.ead.mobileapp.dto.product

import com.ead.mobileapp.models.Product
import java.io.Serializable

data class ProductResponse(
    val status: String,
    val data: List<Product>
) : Serializable
