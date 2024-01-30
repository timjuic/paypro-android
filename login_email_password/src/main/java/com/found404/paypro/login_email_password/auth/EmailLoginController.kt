package com.found404.paypro.login_email_password.auth

import android.content.Context
import androidx.activity.ComponentActivity
import com.found404.core.auth.LoginCredentials
import com.found404.paypro.login_email_password.ui.InputState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmailLoginController {
    private fun isValidEmail(emailState: InputState): Boolean {
        val emailRegex = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}${'$'}""".toRegex()
        emailState.isError = !emailRegex.matches(emailState.text)

        return !emailState.isError
    }

    private fun isValidPassword(passwordState: InputState): Boolean {
        passwordState.isError = passwordState.text.length < 8

        return !passwordState.isError
    }

    fun validation(emailState: InputState, passwordState: InputState): Boolean {
        isValidEmail(emailState)
        isValidPassword(passwordState)
        return !emailState.isError && !passwordState.isError
    }

    fun performLogin(
        email: String, password: String, context: Context
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val loginCredentials = LoginCredentials(email, password)
            AuthProviderHolder.credentialsAuthProvider.loginUser(
                "/api/auth/login", loginCredentials
            )
            (context as? ComponentActivity)?.finish()
        }
    }
}