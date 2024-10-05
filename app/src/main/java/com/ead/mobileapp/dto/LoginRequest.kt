package com.ead.mobileapp.dto

import com.google.gson.annotations.SerializedName


data class LoginRequest(
    var email: String,
    var password: String
)