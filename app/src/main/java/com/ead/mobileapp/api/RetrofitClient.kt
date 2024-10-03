package com.ead.mobileapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dummyjson.com/" // Replace with your API base URL

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // For JSON to Kotlin conversion
            .build()
    }

//    val apiService: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
}
