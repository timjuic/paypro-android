package com.Found404.paypro

import com.Found404.paypro.responses.RegistrationResponse

interface RegistrationService {
    suspend fun registerUser(endpointPath: String, firstName: String, lastName: String, email: String, password:String) : RegistrationResponse
}