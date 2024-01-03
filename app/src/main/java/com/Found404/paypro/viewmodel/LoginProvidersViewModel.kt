package com.Found404.paypro.viewmodel

import androidx.lifecycle.ViewModel
import com.found404.core.AppConfig
import com.found404.core.AuthModule
import com.found404.paypro.login_email_password.CredentialsAuthProvider
import com.found404.paypro.login_google.GoogleAuthProvider

class LoginProvidersViewModel : ViewModel() {
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
}
