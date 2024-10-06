package com.ead.mobileapp.api.interfaces

import com.ead.mobileapp.api.ApiResponse
import com.ead.mobileapp.dto.auth.LoginRequest
import com.ead.mobileapp.dto.auth.LoginResponse
import com.ead.mobileapp.dto.auth.RegisterRequest
import com.ead.mobileapp.dto.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ApiResponse<LoginResponse>>

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<ApiResponse<RegisterResponse>>
}
