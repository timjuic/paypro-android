package com.found404.paypro.login_google.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleSignInClientProvider(private val context: Context) {
    fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("607646132014-ejsoniv7gq37bcvrcf4scm960kftocu4.apps.googleusercontent.com")
            .requestEmail().build()
        return GoogleSignIn.getClient(context, gso)
    }
}