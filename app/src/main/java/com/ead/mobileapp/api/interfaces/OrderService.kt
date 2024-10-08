package com.ead.mobileapp.api.interfaces
import com.ead.mobileapp.api.ApiResponse
import com.ead.mobileapp.models.Order
import retrofit2.Response
import retrofit2.http.*

interface OrderService {
    @POST("/place-order")
    suspend fun placeOrder(@Query("email") email: String): Response<Void>

    @GET("/orders")
    suspend fun getOrders(@Query("email") email: String): Response<ApiResponse<List<Order>>>

    @POST("request-cancel-order")
    suspend fun requestCancelOrder(@Query("orderId") orderId: String): Response<Void>
}
