package com.found404.paypro.login_google.auth

import android.content.Context
import android.content.Intent
import com.found404.core.auth.AuthCallbacks
import com.found404.core.auth.AuthModule
import com.found404.core.auth.LoginResponse
import com.found404.core.exceptions.ServerUnreachableException
import com.found404.paypro.login_google.R
import com.found404.paypro.login_google.network.OkHttpClientNetworkClient
import com.found404.paypro.login_google.parser.GsonJsonParser
import com.found404.paypro.login_google.ui.GoogleLoginActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object AuthProviderHolder {
    lateinit var authCallbacks: AuthCallbacks<LoginResponse>
    lateinit var googleAuthProvider: GoogleAuthProvider
}


class GoogleAuthProvider(private val baseUrl: String) : AuthModule<String, LoginResponse> {
    private val networkClient = OkHttpClientNetworkClient()
    private val jsonParser = GsonJsonParser()

    override suspend fun loginUser(
        endpointPath: String, loginCredentials: String
    ) {
        val mediaType = "text/plain".toMediaTypeOrNull()
        val requestBody = loginCredentials.toRequestBody(mediaType)

        val request = Request.Builder().url("${baseUrl}${endpointPath}").post(requestBody).build()

        try {
            val responseBody = networkClient.performRequest(request)
            val loginResponse =
                responseBody?.let { jsonParser.fromJson(it, LoginResponse::class.java) }

            if (loginResponse?.success == true) {
                AuthProviderHolder.authCallbacks.onSuccessfulLogin(loginResponse)
            } else {
                AuthProviderHolder.authCallbacks.onFailedLogin(loginResponse!!)
            }
        } catch (e: Exception) {
            AuthProviderHolder.authCallbacks.onServerUnreachable(ServerUnreachableException("Server couldn't be reached! Please try again later."))
        }
    }

    override fun initializeState(callbacks: AuthCallbacks<LoginResponse>) {
        AuthProviderHolder.authCallbacks = callbacks
        AuthProviderHolder.googleAuthProvider = this
    }

    override fun startActivity(context: Context) {
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