package com.Found404.paypro.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.found404.core.AppConfig
import com.found404.core.AuthModule
import com.found404.paypro.login_google.GoogleAuthProvider
import com.found404.paypro.login_google.GoogleSignInResultHandler
import com.found404.paypro.login_google.GoogleSignInResultListener


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

        //modules.add(CredentialsAuthProvider(AppConfig.BASE_URL))
        modules.add(googleAuthProvider)
        // ADD MORE MODULES AS NEEDED

        return modules
    }

    override fun onGoogleSignInResult(data: Intent?) {
        GoogleSignInResultHandler.handleSignInResult(data, googleAuthProvider)
    }
}
