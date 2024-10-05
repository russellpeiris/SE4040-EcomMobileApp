package com.ead.mobileapp.dto.product

import com.ead.mobileapp.models.Product
import java.io.Serializable

data class FeedbackRequest(
    val email: String,
    val comment: String,
    val rating: Int,
    val product: Product
) : Serializable

