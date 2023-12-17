package com.Found404.paypro

data class UserData(
    val userId: String?,
    val userEmail: String?,
    val jwtToken: String?,
    val refreshToken: String?
)