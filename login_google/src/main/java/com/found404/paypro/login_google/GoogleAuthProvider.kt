package com.found404.paypro.login_google

import androidx.compose.runtime.Composable
import com.found404.core.AuthCallbacks
import com.found404.core.AuthModule
import com.found404.core.models.LoginResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient

class GoogleAuthProvider(private val baseUrl: String) : AuthModule<String, LoginResponse> {
    private val gson = Gson()
    private val client = OkHttpClient()
    override suspend fun loginUser(
        endpointPath: String,
        loginCredentials: String,
        callbacks: AuthCallbacks<LoginResponse>
    ) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun DisplayButton() {
        TODO("Not yet implemented")
    }
}