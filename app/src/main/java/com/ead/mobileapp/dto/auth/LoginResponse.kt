package com.ead.mobileapp.dto.auth

import com.ead.mobileapp.models.User
import java.io.Serializable


data class LoginResponse (
    val email: String,
    val token: String,
    val name: String,
    val address: String,
) : Serializable