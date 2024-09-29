package com.ead.mobileapp.models

import java.io.Serializable

data class CartItem(val name: String, val price: Double, val quantity: Int) : Serializable
