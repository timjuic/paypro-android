package com.Found404.paypro

import android.content.Context
import com.Found404.paypro.responses.LoginResponse
import com.Found404.paypro.responses.RegistrationResponse

interface AuthService {
    suspend fun registerUser(firstName: String, lastName: String, email: String, password:String) : RegistrationResponse

    suspend fun loginUser(email: String, password: String, context: Context): LoginResponse
}