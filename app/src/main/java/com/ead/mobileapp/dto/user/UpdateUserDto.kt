package com.ead.mobileapp.dto.user

data class UpdateUserDto(
    val name: String?,
    val email: String?,
    val mobileNumber: String?,
    val address: String?,
    val isActivated: Boolean,
    )