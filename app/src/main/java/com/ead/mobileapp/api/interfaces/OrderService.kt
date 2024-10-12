package com.ead.mobileapp.api.interfaces
import com.ead.mobileapp.api.ApiResponse
import com.ead.mobileapp.dto.order.OrderRequest
import com.ead.mobileapp.models.Order
import retrofit2.Response
import retrofit2.http.*

interface OrderService {
    @GET("/api/Order/GenerateOrder")
    suspend fun placeOrder(@Query("email") email:String): Response<Void>

 abstract    @GET("/api/Order/GetAllOrders")
    suspend fun getOrders(@Query("email") email: String): Response<ApiResponse<List<Order>>>

    @PUT("/api/Order/CancelItem/{itemId}")
    suspend fun requestCancelOrder(@Path("itemId") itemId: String): Response<Void>

}
