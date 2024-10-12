package com.ead.mobileapp.dto.auth

class RegisterRequest(
    private val name: String,
    private val email: String,
    private val password: String,
    private val userRole: String = "Customer"
)
