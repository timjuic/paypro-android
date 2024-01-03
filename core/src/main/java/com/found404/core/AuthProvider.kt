package com.found404.core

interface AuthProvider<LoginCredentialsType, LoginResponseType> {
    suspend fun loginUser(
        endpointPath: String,
        loginCredentials: LoginCredentialsType,
        callbacks: AuthCallbacks<LoginResponseType>
    )
}