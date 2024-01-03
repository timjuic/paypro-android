package com.found404.paypro.login_google

import android.content.Context
import android.widget.Toast
import com.found404.core.AuthCallbacks
import com.found404.core.AuthModule
import com.found404.core.exceptions.ServerUnreachableException
import com.found404.core.models.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class GoogleAuthProvider(private val baseUrl: String) : AuthModule<String, LoginResponse> {
    private val gson = Gson()
    private val client = OkHttpClient()
    override suspend fun loginUser(
        endpointPath: String,
        loginCredentials: String,
        callbacks: AuthCallbacks<LoginResponse>
    ) {
        val mediaType = "text/plain".toMediaTypeOrNull()
        val requestBody = loginCredentials.toRequestBody(mediaType)

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
                    callbacks.onSuccessfulLogin(loginResponse)
                } else {
                    callbacks.onFailedLogin(loginResponse)
                }
            } catch (e: Exception) {
                callbacks.onServerUnreachable(ServerUnreachableException("Server couldn't be reached! Please try again later."))
            }
        }
    }

    override fun onButtonClick(context: Context) {
        Toast.makeText(context, "aaas", Toast.LENGTH_SHORT).show()
    }

    override fun getButtonLayout(context: Context): Int {
        return R.layout.button_layout
    }
}