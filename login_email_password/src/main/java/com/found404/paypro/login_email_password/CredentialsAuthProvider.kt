package com.found404.paypro.login_email_password

import com.found404.core.exceptions.ServerUnreachableException
import com.found404.core.models.AuthCallbacks
import com.found404.core.models.LoginCredentials
import com.found404.core.models.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class CredentialsAuthProvider(private val baseUrl: String) : AuthProvider<LoginCredentials, LoginResponse> {
    private val gson = Gson()
    private val client = OkHttpClient()

    override suspend fun loginUser(
        endpointPath: String,
        loginCredentials: LoginCredentials,
        callbacks: AuthCallbacks<LoginResponse>
    ) {
        val requestBody = gson.toJson(
            mapOf(
                "emailAddress" to loginCredentials.email,
                "password" to LoginUtils.hashPassword(loginCredentials.password)
            )
        ).toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("${baseUrl}${endpointPath}")
            .post(requestBody)
            .build()

        withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                val loginResponse = gson.fromJson(responseBody, LoginResponse::class.java)

                if (loginResponse.success) {
//                    userDataService.saveLoggedInUser(loginResponse.data, context)
                    callbacks.onSuccessfulLogin(loginResponse)
                } else {
                    callbacks.onFailedLogin(loginResponse.errorMessage ?: "Login failed")
                }
            } catch (e: Exception) {
                callbacks.onServerUnreachable(ServerUnreachableException("Server couldn't be reached! Please try again later."))
            }
        }
    }
}