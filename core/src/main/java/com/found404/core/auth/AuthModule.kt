package com.found404.core.auth


interface AuthModule<LoginCredentialsType, LoginResponseType> :
    AuthProvider<LoginCredentialsType, LoginResponseType>, AuthDisplayable {
    fun initializeState(callbacks: AuthCallbacks<LoginResponse>)
}