package com.Found404.paypro

data class RegistrationResult(
    val success: Boolean,
    val message: String,
    val errorCode: Int? = null,
    val errorMessage: String? = null,
    val error: String? = null
)