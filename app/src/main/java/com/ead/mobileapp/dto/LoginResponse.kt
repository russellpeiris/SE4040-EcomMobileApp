package com.ead.mobileapp.dto

import com.ead.mobileapp.models.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class LoginResponse (
    val email : String,
) : Serializable