package com.ead.mobileapp.api

import com.ead.mobileapp.dto.ApiResponse
import com.ead.mobileapp.dto.LoginRequest
import com.ead.mobileapp.dto.LoginResponse
import com.ead.mobileapp.dto.RegisterRequest
import com.ead.mobileapp.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ApiResponse<LoginResponse>>

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<ApiResponse<RegisterResponse>>
}