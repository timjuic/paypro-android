package com.found404.paypro.login_google

import android.content.Intent
import android.util.Log
import com.found404.core.auth.AuthCallbacks
import com.found404.core.auth.LoginResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

object GoogleSignInResultHandler {
    suspend fun handleSignInResult(data: Intent?, authProvider: GoogleAuthProvider, authCallback: AuthCallbacks<LoginResponse>) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            authProvider.loginUser("/api/auth/google", account.idToken.toString(), authCallback)

//            Log.d("GoogleSignIn", "Display Name: ${account.displayName}")
//            Log.d("GoogleSignIn", "Given Name: ${account.givenName}")
//            Log.d("GoogleSignIn", "Family Name: ${account.familyName}")
//            Log.d("GoogleSignIn", "Email: ${account.email}")
//            Log.d("GoogleSignIn", "Id: ${account.id}")
//            Log.d("GoogleSignIn", "IdToken: ${account.idToken}")
//            Log.d("GoogleSignIn", "Photo URL: ${account.photoUrl}")
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w("GoogleSignIn", "Sign-In Failed: ${e.statusCode}")
        }
    }
}