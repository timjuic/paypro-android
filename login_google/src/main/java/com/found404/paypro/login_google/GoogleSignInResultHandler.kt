package com.found404.paypro.login_google

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

object GoogleSignInResultHandler {
    suspend fun handleSignInResult(context: Context, data: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val authCallbacks = AuthProviderHolder.authCallbacks
            val googleAuthProvider = AuthProviderHolder.googleAuthProvider

            googleAuthProvider!!.loginUser(
                "/api/auth/google",
                account.idToken.toString(),
                authCallbacks!!
            )
        } catch (e: ApiException) {
            Toast.makeText(context, "Please use a different sign in method.", Toast.LENGTH_SHORT)
                .show()
            Log.w("GoogleSignIn", "Sign-In Failed: ${e.statusCode}")
        }
    }
}