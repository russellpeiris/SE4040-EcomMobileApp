package com.ead.mobileapp.models

import java.io.Serializable

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val address: String,
    val role: String,
    val mobileNumber: String,
    val isActivated: Boolean,
) : Serializable