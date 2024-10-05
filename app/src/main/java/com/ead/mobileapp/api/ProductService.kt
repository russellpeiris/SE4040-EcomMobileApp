package com.ead.mobileapp.api

import com.ead.mobileapp.models.CartItem
import com.ead.mobileapp.models.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("api/products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("api/products/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product>

    @POST("api/products")
    suspend fun createProduct(@Body product: Product): Response<Product>

    @PUT("api/products/{id}")
    suspend fun updateProduct(@Path("id") id: String, @Body product: Product): Response<Product>

    @DELETE("api/products/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<Void>

    @POST("api/feedback")
    fun submitFeedback(@Body feedback: VendorFeedbackRequest): Response<Void>

    @POST("api/cart")
    fun addToCart(@Body addToCartRequest: CartItem): Response<Void>
}

data class VendorFeedbackRequest(
    val productId: String,
    val rating: Float,
    val comment: String
)
