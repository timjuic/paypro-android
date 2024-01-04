package com.Found404.paypro

data class UserData(
    val userId: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val jwtToken: String?,
    val refreshToken: String?
)