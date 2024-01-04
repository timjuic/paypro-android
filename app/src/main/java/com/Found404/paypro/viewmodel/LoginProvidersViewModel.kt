package com.Found404.paypro.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.found404.core.AppConfig
import com.found404.core.auth.AuthCallbacks
import com.found404.core.auth.AuthModule
import com.found404.core.auth.LoginResponse
import com.found404.paypro.login_email_password.CredentialsAuthProvider
import com.found404.paypro.login_google.GoogleAuthProvider
import com.found404.paypro.login_google.GoogleSignInResultHandler
import com.found404.paypro.login_google.GoogleSignInResultListener
import kotlinx.coroutines.launch


class LoginProvidersViewModel : ViewModel(), GoogleSignInResultListener {
    private val googleAuthProvider = GoogleAuthProvider(AppConfig.BASE_URL)

    init {
        googleAuthProvider.signInResultListener = this
    }

    val authModules: List<AuthModule<*, *>> by lazy {
        getModules()
    }

    private fun getModules(): List<AuthModule<*, *>> {
        val modules = mutableListOf<AuthModule<*, *>>()

        modules.add(CredentialsAuthProvider(AppConfig.BASE_URL))
        modules.add(GoogleAuthProvider(AppConfig.BASE_URL))
        // ADD MORE MODULES AS NEEDED

        return modules
    }

    override fun onGoogleSignInResult(data: Intent?, authCallback: AuthCallbacks<LoginResponse>) {
        viewModelScope.launch{
            GoogleSignInResultHandler.handleSignInResult(data, googleAuthProvider, authCallback)
        }
    }
}
