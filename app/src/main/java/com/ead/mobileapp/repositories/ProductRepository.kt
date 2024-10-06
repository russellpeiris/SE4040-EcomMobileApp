package com.ead.mobileapp.repositories

import com.ead.mobileapp.api.ProductService
import com.ead.mobileapp.dto.cart.AddToCartRequest
import com.ead.mobileapp.dto.cart.AddToCartResponse
import com.ead.mobileapp.dto.cart.CartResponse
import com.ead.mobileapp.models.CartItem
import com.ead.mobileapp.models.FeedBack
import com.ead.mobileapp.models.Order
import com.ead.mobileapp.models.Product
import retrofit2.Response

class ProductRepository(private val productService: ProductService) {

    suspend fun getProducts(): List<Product>? {
        val response = productService.getProducts()
        return if (response.isSuccessful) {

            response.body()?.data
        } else {
            emptyList()
        }
    }

    suspend fun  addToCart(email: String, product: Product): Response<AddToCartResponse> {
        return productService.addToCart(AddToCartRequest(email, product))
    }

    suspend fun getCartItems(email: String): CartResponse ? {
        println("email: $email")
        val response = productService.getCartItems(email)
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }

    suspend fun getProductFeedback(id: String, email: String): FeedBack? {
        val response = productService.getProductFeedback(id, email)
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            return null
        }
    }

    suspend fun addProductFeedback(productFeedback: FeedBack): Response<Void> {
        return productService.addProductFeedback(productFeedback)
    }

    suspend fun placeOrder(email: String): Response<Void> {
        return productService.placeOrder(email)
    }

    suspend fun getOrders(email: String): List<Order>? {
        val response = productService.getOrders(email)
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            emptyList()
        }
    }

    suspend fun requestCancelOrder(orderId: String): Response<Void> {
        return productService.requestCancelOrder(orderId)
    }
}
