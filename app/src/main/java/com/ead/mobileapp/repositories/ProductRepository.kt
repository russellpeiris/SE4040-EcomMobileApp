package com.ead.mobileapp.repositories

import com.ead.mobileapp.api.ProductService
import com.ead.mobileapp.dto.cart.AddToCartRequest
import com.ead.mobileapp.dto.cart.AddToCartResponse
import com.ead.mobileapp.dto.cart.CartResponse
import com.ead.mobileapp.dto.product.FeedbackRequest
import com.ead.mobileapp.models.CartItem
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

    suspend fun getCartItems(email: String): Response<CartResponse> {
        return productService.getCartItems(email)
    }

    suspend fun addProductFeedback(productFeedback: FeedbackRequest): Response<Void> {
        return productService.addProductFeedback(productFeedback)
    }
}
