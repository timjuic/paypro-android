package com.Found404.paypro

import android.content.Context
import com.Found404.paypro.responses.LoginResponse

interface AuthenticationService {

    suspend fun loginUser(
        endpointPath: String,
        email: String,
        password: String,
        context: Context
    ): LoginResponse

    suspend fun isJwtValid(context: Context): Boolean

    suspend fun refreshAccessToken(endpointPath: String, refreshToken: String): String?
}