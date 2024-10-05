package com.ead.mobileapp.api
import com.ead.mobileapp.models.Order
import com.ead.mobileapp.models.OrderUpdateRequest
import retrofit2.Response
import retrofit2.http.*

interface OrderService {

    @POST("api/orders")
    suspend fun createOrder(@Body order: Order): Response<Order>

    @GET("api/orders/{id}")
    suspend fun getOrderById(@Path("id") id: String): Response<Order>
}
