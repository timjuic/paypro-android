package com.found404.paypro.login_email_password

import com.found404.core.models.AuthCallbacks

interface AuthProvider<LoginCredentialsType, LoginResponseType> {
    suspend fun loginUser(
        endpointPath: String,
        loginCredentials: LoginCredentialsType,
        callbacks: AuthCallbacks<LoginResponseType>
    )
}
