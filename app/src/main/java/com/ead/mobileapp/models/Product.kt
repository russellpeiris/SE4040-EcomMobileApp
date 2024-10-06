package com.ead.mobileapp.models

import android.net.Uri
import java.io.Serializable


data class Product(
    val _id: String,
    val name: String,
    val price: String,
    val imageUrl: String,
    val description: String,
    val category: String,
) : Serializable