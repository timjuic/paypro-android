package com.found404.paypro.login_google

import android.content.Context
import android.content.Intent
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

interface GoogleSignInResultListener {
    fun onGoogleSignInResult(data: Intent?, authCallback: AuthCallbacks<LoginResponse>)
}

class GoogleAuthProvider(private val baseUrl: String) : AuthModule<String, LoginResponse> {
    private val gson = Gson()
    private val client = OkHttpClient()
    var signInResultListener: GoogleSignInResultListener? = null

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

    override fun onButtonClick(context: Context, authCallback: AuthCallback, signInLauncher: ActivityResultLauncher<Intent>) {
        val googleSignInClient = GoogleSignInClientProvider.getGoogleSignInClient(context)
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
        // Navigate to some page if needed
//        authCallback.navigateTo("somePage")
    }

    override fun getButtonLayout(context: Context): Int {
        return R.layout.button_layout
    }

    override fun getButtonId(): Int {
        return R.id.myButton
    }
}