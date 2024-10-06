package com.ead.mobileapp.repositories

import com.ead.mobileapp.api.interfaces.ProductService
import com.ead.mobileapp.models.FeedBack
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
}
