package com.ead.mobileapp.models

import java.io.Serializable


data class Product(
    val name: String,
    val price: String,
    val description: String,
    val category: String
) : Serializable