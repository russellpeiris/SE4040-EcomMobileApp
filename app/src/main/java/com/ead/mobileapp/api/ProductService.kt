package com.ead.mobileapp.api

import com.ead.mobileapp.dto.cart.AddToCartRequest
import com.ead.mobileapp.dto.cart.AddToCartResponse
import com.ead.mobileapp.dto.cart.CartResponse
import com.ead.mobileapp.dto.product.ProductResponse
import com.ead.mobileapp.models.CartItemResponse
import com.ead.mobileapp.models.FeedBack
import com.ead.mobileapp.models.Order
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
    suspend fun getCartItems(@Query("email") email: String): Response<ApiResponse<CartResponse>>

    @POST("/product-feedback")
    suspend fun addProductFeedback(@Body body: FeedBack): Response<Void>

    @GET("/product-feedback")
    suspend fun getProductFeedback(
        @Query("productId") productId: String,
        @Query("email") email: String
    ): Response<ApiResponse<FeedBack>>

    @POST("/place-order")
    suspend fun placeOrder(@Query("email") email: String): Response<Void>

    @GET("/orders")
    suspend fun getOrders(@Query("email") email: String): Response<ApiResponse<List<Order>>>

    @POST("request-cancel-order")
    suspend fun requestCancelOrder(@Query("orderId") orderId: String): Response<Void>

}
