package com.ead.mobileapp.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.ead.mobileapp.api.ErrorResponse
import com.google.gson.Gson

class Utils {

    companion object {
        fun parseErrorMessage(errorBody: String): String? {
            return try {
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                errorResponse.message
            } catch (e: Exception) {
                null
            }
        }
        fun decodeJwtToken(token: String): Map<String, Any?>? {
            val secret = "my-very-secure-secret-key-21277054-SY-S1"
            return try {
                val algorithm = Algorithm.HMAC256(secret)
                val verifier = JWT.require(algorithm).build()
                val decodedJWT: DecodedJWT = verifier.verify(token)

                mapOf(
                    "subject" to decodedJWT.subject,
                    "email" to decodedJWT.getClaim("email").asString(),
                    "role" to decodedJWT.getClaim("role").asString(),
                    "name" to decodedJWT.getClaim("name").asString(),
                    "mobileNumber" to decodedJWT.getClaim("mobileNumber").asString(),
                    "address" to decodedJWT.getClaim("address").asString(),
                    "issuedAt" to decodedJWT.issuedAt,
                    "expiresAt" to decodedJWT.expiresAt,
                    "issuer" to decodedJWT.issuer,
                    "audience" to decodedJWT.audience
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    }
}