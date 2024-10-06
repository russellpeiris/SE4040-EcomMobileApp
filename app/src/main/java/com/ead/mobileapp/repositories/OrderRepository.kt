package com.ead.mobileapp.repositories
import com.ead.mobileapp.api.interfaces.OrderService
import com.ead.mobileapp.models.Order
import retrofit2.Response

class OrderRepository(private val orderService: OrderService) {

    suspend fun placeOrder(email: String): Response<Void> {
        return orderService.placeOrder(email)
    }

    suspend fun getOrders(email: String): List<Order>? {
        val response = orderService.getOrders(email)
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            emptyList()
        }
    }

    suspend fun requestCancelOrder(orderId: String): Response<Void> {
        return orderService.requestCancelOrder(orderId)
    }
}