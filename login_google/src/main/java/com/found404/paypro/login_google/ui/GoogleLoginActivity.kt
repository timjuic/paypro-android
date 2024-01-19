package com.found404.paypro.login_google.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.found404.core.auth.AuthModule
import com.found404.core.auth.LoginResponse
import com.found404.paypro.login_google.auth.AuthProviderHolder
import com.found404.paypro.login_google.auth.GoogleSignInClientProvider
import com.found404.paypro.login_google.auth.GoogleSignInResultHandler
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleLoginActivity : ComponentActivity() {
    private lateinit var googleSignInClientProvider: GoogleSignInClientProvider
    private lateinit var googleSignInResultHandler: GoogleSignInResultHandler
    private lateinit var authModule: AuthModule<String, LoginResponse>

    private val signInResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                CoroutineScope(Dispatchers.Main).launch {
                    googleSignInResultHandler.handleSignInResult(data)
                    finish()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authModule = AuthProviderHolder.googleAuthProvider
        googleSignInClientProvider = GoogleSignInClientProvider(this)
        googleSignInResultHandler = GoogleSignInResultHandler(this, authModule)

        val googleSignInClient = googleSignInClientProvider.getGoogleSignInClient()

        setContent {
            GoogleLoginPage(onSignInRequest = { signIn(googleSignInClient) })
        }
    }

    private fun signIn(googleSignInClient: GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent
        signInResultLauncher.launch(signInIntent)
    }
}
