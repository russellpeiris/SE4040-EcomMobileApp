package com.ead.mobileapp.api.interfaces

import com.ead.mobileapp.api.ApiResponse
import com.ead.mobileapp.dto.cart.AddToCartRequest
import com.ead.mobileapp.dto.cart.AddToCartResponse
import com.ead.mobileapp.dto.cart.CartResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface CartService {

    @PUT("api/Cart/UpdateCart")
    suspend fun addToCart(@Query("email") email: String, @Body body: AddToCartRequest): Response<AddToCartResponse>

    @GET("/api/Cart/GetByCartEmail")
    suspend fun getCartItems(@Query("email") email: String): Response<ApiResponse<CartResponse>>
}