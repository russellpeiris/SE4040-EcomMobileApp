package com.ead.mobileapp.repositories

import com.ead.mobileapp.api.interfaces.CartService
import com.ead.mobileapp.dto.cart.AddToCartRequest
import com.ead.mobileapp.dto.cart.AddToCartResponse
import com.ead.mobileapp.dto.cart.CartResponse
import com.ead.mobileapp.models.CartItem
import com.ead.mobileapp.models.Product
import retrofit2.Response

class CartRepository(private val cartService: CartService) {

    suspend fun  addToCart(email: String, product: Product): Response<AddToCartResponse> {
        return cartService.addToCart(email, AddToCartRequest(
            product.id,
            product.name,
            product.price,
            product.imageUrl,
            product.description,
            product.category,
            product.vendorId,
            product.isActivated
        ))
    }

    suspend fun getCartItems(email: String): CartResponse? {
        val response = cartService.getCartItems(email)
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }

}