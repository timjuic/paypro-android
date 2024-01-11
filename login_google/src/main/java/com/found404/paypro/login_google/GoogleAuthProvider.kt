package com.found404.paypro.login_google

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.found404.core.auth.AuthCallback
import com.found404.core.auth.AuthCallbacks
import com.found404.core.auth.AuthModule
import com.found404.core.auth.LoginResponse
import com.found404.core.exceptions.ServerUnreachableException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object AuthProviderHolder {
    var authCallbacks: AuthCallbacks<LoginResponse>? = null
    var googleAuthProvider: GoogleAuthProvider? = null
}


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

    override fun onButtonClick(context: Context, authCallback: AuthCallback, callbacks: AuthCallbacks<LoginResponse>) {
        AuthProviderHolder.authCallbacks = callbacks
        AuthProviderHolder.googleAuthProvider = this
        val intent = Intent(context, GoogleLoginActivity::class.java)
        context.startActivity(intent)
    }

    override fun getButtonLayout(context: Context): Int {
        return R.layout.button_layout
    }

    override fun getButtonId(): Int {
        return R.id.myButton
    }
}