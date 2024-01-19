package com.found404.paypro.login_email_password.auth


import android.content.Context
import android.content.Intent
import com.found404.core.auth.AuthCallbacks
import com.found404.core.auth.AuthModule
import com.found404.core.auth.LoginCredentials
import com.found404.core.auth.LoginResponse
import com.found404.core.exceptions.ServerUnreachableException
import com.found404.paypro.login_email_password.ui.EmailLoginActivity
import com.found404.paypro.login_email_password.R
import com.found404.paypro.login_email_password.network.OkHttpClientNetworkClient
import com.found404.paypro.login_email_password.parser.GsonJsonParser
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object AuthProviderHolder {
    lateinit var authCallbacks: AuthCallbacks<LoginResponse>
    lateinit var credentialsAuthProvider: CredentialsAuthProvider
}


class CredentialsAuthProvider(private val baseUrl: String) :
    AuthModule<LoginCredentials, LoginResponse> {
    private val networkClient = OkHttpClientNetworkClient()
    private val jsonParser = GsonJsonParser()

    override suspend fun loginUser(
        endpointPath: String, loginCredentials: LoginCredentials
    ) {
        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody = jsonParser.toJson(
            mapOf(
                "emailAddress" to loginCredentials.email,
                "password" to LoginUtils.hashPassword(loginCredentials.password)
            )
        ).toRequestBody("application/json".toMediaType())

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
        AuthProviderHolder.credentialsAuthProvider = this
    }

    override fun startActivity(context: Context) {
        val intent = Intent(context, EmailLoginActivity::class.java)
        context.startActivity(intent)
    }

    override fun getButtonLayout(context: Context): Int {
        return R.layout.button_email_layout
    }

    override fun getButtonId(): Int {
        return R.id.btn_email_login
    }
}