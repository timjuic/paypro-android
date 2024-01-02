package com.found404.paypro.login_email_password

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.found404.core.AuthCallbacks
import com.found404.core.models.LoginCredentials
import com.found404.core.models.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class CredentialsAuthProvider(private val baseUrl: String) : AuthProvider<LoginCredentials, LoginResponse>, AuthDisplayable {
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

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                val loginResponse = gson.fromJson(responseBody, LoginResponse::class.java)

                if (loginResponse.success) {
//                    userDataService.saveLoggedInUser(loginResponse.data, context)
                    callbacks.onSuccessfulLogin(loginResponse)
                } else {
                    callbacks.onFailedLogin(loginResponse)
                }

        }
    }


    @Composable
    override fun DisplayButton(authProviderClickListener: AuthProviderClickListener) {
        Button(onClick = {
            authProviderClickListener.onAuthProviderClick()
        }) {
            Text("Credentials Auth")
        }
    }
}