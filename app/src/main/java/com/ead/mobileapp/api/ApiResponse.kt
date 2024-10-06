package com.ead.mobileapp.api

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T?
)
