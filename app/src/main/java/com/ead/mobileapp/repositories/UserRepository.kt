package com.ead.mobileapp.repositories

import com.ead.mobileapp.api.interfaces.AuthService
import com.ead.mobileapp.dto.user.UpdateUserDto

class UserRepository(private val authService: AuthService) {

    suspend fun updateUser(updateUserDto: UpdateUserDto): UpdateUserDto? {
        val response = authService.updateUser(updateUserDto)

        if (!response.isSuccessful) {
            throw Exception("Failed to update user")
        } else {
            return response.body()?.data
        }
    }
}