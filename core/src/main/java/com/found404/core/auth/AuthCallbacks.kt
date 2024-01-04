package com.found404.core.auth

interface AuthCallbacks<AuthResponseType> {
    fun onSuccessfulLogin(response: AuthResponseType)
    fun onFailedLogin(response: AuthResponseType)
    fun onServerUnreachable(error: Throwable)
}