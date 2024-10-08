package com.ead.mobileapp.api

import com.ead.mobileapp.api.interfaces.AuthService
import com.ead.mobileapp.api.interfaces.CartService
import com.ead.mobileapp.api.interfaces.OrderService
import com.ead.mobileapp.api.interfaces.ProductService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private var mHttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private var mOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(mHttpLoggingInterceptor)
        .build()

    private var mRetrofit: Retrofit? = null

    private val client: Retrofit?
        get() {
            if(mRetrofit == null){
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return mRetrofit
        }

    val authService: AuthService
        get() {
            return client!!.create(AuthService::class.java)
        }

    val productService: ProductService
        get() {
            return client!!.create(ProductService::class.java)
        }

    val orderService: OrderService
        get() {
            return client!!.create(OrderService::class.java)
        }

    val cartService: CartService
        get() {
            return client!!.create(CartService::class.java)
        }
}

