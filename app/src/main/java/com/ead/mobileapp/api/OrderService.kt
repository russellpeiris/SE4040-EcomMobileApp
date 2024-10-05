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

    @PUT("api/orders/{id}")
    suspend fun updateOrder(@Path("id") id: String, @Body orderUpdate: OrderUpdateRequest): Response<Order>

    @DELETE("api/orders/{id}")
    suspend fun cancelOrder(@Path("id") id: String): Response<Void>
}
