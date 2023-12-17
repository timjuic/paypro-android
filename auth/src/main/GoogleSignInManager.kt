package com.found404.auth
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class GoogleSignInManager(
    private val context: Context,
    private val authRepository: AuthRepository
) : AuthManager {

    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.server_client_id))
            .requestEmail()
            .build()
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(context, googleSignInOptions)
    }

    override fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    override fun handleSignInResult(data: Intent?, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                onSuccess(idToken)
            } else {
                onFailure(NullPointerException("ID token is null"))
            }
        } catch (e: ApiException) {
            onFailure(e)
        }
    }

    companion object {
        const val RC_SIGN_IN = 100
    }
}
