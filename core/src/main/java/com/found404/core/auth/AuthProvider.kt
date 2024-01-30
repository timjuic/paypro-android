package com.found404.core.auth

interface AuthProvider<LoginCredentialsType, LoginResponseType> {
    suspend fun loginUser(
        endpointPath: String,
        loginCredentials: LoginCredentialsType
    )
}