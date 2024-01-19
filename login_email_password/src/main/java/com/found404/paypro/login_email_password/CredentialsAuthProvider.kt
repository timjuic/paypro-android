package com.found404.paypro.login_email_password


import android.content.Context
import android.content.Intent
import com.found404.core.auth.AuthCallbacks
import com.found404.core.auth.AuthModule
import com.found404.core.auth.LoginCredentials
import com.found404.core.auth.LoginResponse
import com.found404.core.exceptions.ServerUnreachableException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object AuthProviderHolder {
    var authCallbacks: AuthCallbacks<LoginResponse>? = null
    var credentialsAuthProvider: CredentialsAuthProvider? = null
}

class CredentialsAuthProvider(private val baseUrl: String) :
    AuthModule<LoginCredentials, LoginResponse> {
    private val gson = Gson()
    private val client = OkHttpClient()

    override suspend fun loginUser(
        endpointPath: String,
        loginCredentials: LoginCredentials
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

//        withContext(Dispatchers.IO) {
//            try {
//                val response = client.newCall(request).execute()
//                val responseBody = response.body?.string()
//                val loginResponse = gson.fromJson(responseBody, LoginResponse::class.java)
//
//                if (loginResponse.success) {
//                    callbacks.onSuccessfulLogin(loginResponse)
//                } else {
//                    callbacks.onFailedLogin(loginResponse)
//                }
//            } catch (e: Exception) {
//                callbacks.onServerUnreachable(ServerUnreachableException("Server couldn't be reached! Please try again later."))
//            }
//        }
    }

    override fun initializeState(callbacks: AuthCallbacks<LoginResponse>) {

    }

    override fun startActivity(context: Context) {

    }

    override fun getButtonLayout(context: Context): Int {
        return R.layout.button_email_layout
    }

    override fun getButtonId(): Int {
        return R.id.btn_email_login
    }
}