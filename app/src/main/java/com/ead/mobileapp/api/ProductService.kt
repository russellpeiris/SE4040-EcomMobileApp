package com.ead.mobileapp.api

import com.ead.mobileapp.dto.product.FeedbackRequest
import com.ead.mobileapp.dto.cart.AddToCartRequest
import com.ead.mobileapp.dto.cart.AddToCartResponse
import com.ead.mobileapp.dto.cart.CartResponse
import com.ead.mobileapp.dto.product.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ProductService {
    @GET("/products")
    suspend fun getProducts():Response<ProductResponse>

    @POST("/cart")
    suspend fun addToCart(@Body body: AddToCartRequest): Response<AddToCartResponse>

    @GET("/cart")
    suspend fun getCartItems(@Query("email") email: String): Response<CartResponse>

    @POST("/product-feedback")
    suspend fun addProductFeedback(@Body feedbackRequest: FeedbackRequest): Response<Void>

    @GET("/product-feedback")
    suspend fun getProductFeedback(@Query("productId") productId: String): Response<ProductResponse>
}
