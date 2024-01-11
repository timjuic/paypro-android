package com.found404.paypro.login_google

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleLoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val googleSignInClient = GoogleSignInClientProvider.getGoogleSignInClient(this)
            GoogleLoginPage(
                context = this,
                googleSignInClient = googleSignInClient,
                onSignInRequest = { client ->
                    signIn(client)
                }
            )
        }
    }

    private fun signIn(googleSignInClient: GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            CoroutineScope(Dispatchers.Main).launch {
                GoogleSignInResultHandler.handleSignInResult(data)
                finish()
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
