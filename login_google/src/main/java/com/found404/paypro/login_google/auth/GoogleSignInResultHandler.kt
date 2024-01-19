package com.found404.paypro.login_google.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.found404.core.auth.AuthModule
import com.found404.core.auth.LoginResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

class GoogleSignInResultHandler(
    private val context: Context, private val authModule: AuthModule<String, LoginResponse>
) {
    suspend fun handleSignInResult(data: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            authModule.loginUser(
                "/api/auth/google",
                account.idToken.toString(),
            )
        } catch (e: ApiException) {
            Toast.makeText(context, "Please use a different sign in method.", Toast.LENGTH_SHORT)
                .show()
            Log.w("GoogleSignIn", "Sign-In Failed: ${e.statusCode}")
        }
    }
}