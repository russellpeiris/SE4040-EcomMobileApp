package com.ead.mobileapp.api.interfaces

import com.ead.mobileapp.api.ApiResponse
import com.ead.mobileapp.dto.product.ProductResponse
import com.ead.mobileapp.models.FeedBack
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ProductService {
    @GET("/api/Product/GetAllProducts")
    suspend fun getProducts():Response<ProductResponse>

    @POST("api/vendorFeedback/CreateVendorFeedback")
    suspend fun addProductFeedback(@Body body: FeedBack): Response<Void>

    @GET("/product-feedback")
    suspend fun getProductFeedback(
        @Query("productId") productId: String,
        @Query("email") email: String
    ): Response<ApiResponse<FeedBack>>

}
