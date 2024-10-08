package com.ead.mobileapp.models

import java.io.Serializable

data class FeedBack (
    val email: String,
    val productId: String,
    val comment: String,
    val rating: Int
) : Serializable