package com.found404.auth
import android.content.Intent
interface AuthManager {
    fun getSignInIntent(): Intent
    fun handleSignInResult(data: Intent?, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit)
}
