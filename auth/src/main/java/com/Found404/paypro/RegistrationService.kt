package com.Found404.paypro

interface RegistrationService {
    suspend fun registerUser(firstName: String, lastName: String, email: String, password:String) : RegistrationResult

}