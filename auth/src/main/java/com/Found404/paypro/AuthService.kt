package com.Found404.paypro

import android.content.Context
import com.Found404.paypro.responses.LoginResponse
import com.Found404.paypro.responses.RegistrationResponse

interface AuthService {
    suspend fun registerUser(endpointPath: String, firstName: String, lastName: String, email: String, password:String) : RegistrationResponse

    suspend fun loginUser(endpointPath: String, email: String, password: String, context: Context): LoginResponse

    suspend fun refreshAccessToken(endpointPath: String, refreshToken: String): String?
}