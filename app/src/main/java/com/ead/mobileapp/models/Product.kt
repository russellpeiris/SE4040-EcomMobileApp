package com.ead.mobileapp.models

import java.io.Serializable


data class Product(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String,
    val description: String,
    val category: String,
    val vendorId: String,
) : Serializable