package com.ead.mobileapp.utils

import com.ead.mobileapp.api.ErrorResponse
import com.google.gson.Gson

class Utils {

    companion object {
        fun parseErrorMessage(errorBody: String): String? {
            return try {
                // Using Gson to parse the error response
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                errorResponse.message
            } catch (e: Exception) {
                null // Return null if parsing fails
            }
        }
    }
}