package com.Found404.paypro

import android.content.Context
import com.Found404.paypro.responses.RegistrationResponse

interface JWTAuthStrategy {

    suspend fun registerUser(endpointPath: String, firstName: String, lastName: String, email: String, password:String) : RegistrationResponse

    suspend fun isJwtValid(context: Context): Boolean

    suspend fun refreshAccessToken(endpointPath: String, refreshToken: String): String?
}