package com.ead.mobileapp.dto

data class ApiResponse<T>(
    val status: Boolean,
    val message: String,
    val data: T?
)
