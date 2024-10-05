package com.ead.mobileapp.repositories

import com.ead.mobileapp.api.ProductService
import com.ead.mobileapp.models.Product

class ProductRepository(private val productService: ProductService) {

    suspend fun getProducts(): List<Product>? {
        val response = productService.getProducts()
        return if (response.isSuccessful) {

            response.body()?.data
        } else {
            emptyList()
        }
    }
}
