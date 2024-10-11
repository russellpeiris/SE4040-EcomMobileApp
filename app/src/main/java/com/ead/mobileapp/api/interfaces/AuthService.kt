package com.ead.mobileapp.api.interfaces

import com.ead.mobileapp.api.ApiResponse
import com.ead.mobileapp.dto.auth.LoginRequest
import com.ead.mobileapp.dto.auth.LoginResponse
import com.ead.mobileapp.dto.auth.RegisterRequest
import com.ead.mobileapp.dto.auth.RegisterResponse
import com.ead.mobileapp.dto.user.UpdateUserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthService {

    @POST("api/UserProfile/LoginUser")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("api/UserProfile/CreateUser")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<ApiResponse<RegisterResponse>>

    @PUT("api/UserProfile/UpdateUser")
    suspend fun updateUser(@Body updateRequest: UpdateUserDto): Response<ApiResponse<UpdateUserDto>>
}
