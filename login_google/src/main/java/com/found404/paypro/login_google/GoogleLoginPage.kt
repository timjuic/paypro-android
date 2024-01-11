package com.found404.paypro.login_google

import android.content.Context
import androidx.compose.runtime.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun GoogleLoginPage(
    context: Context,
    googleSignInClient: GoogleSignInClient,
    onSignInRequest: (GoogleSignInClient) -> Unit
) {
    LaunchedEffect(Unit) {
        onSignInRequest(googleSignInClient)
    }
}
