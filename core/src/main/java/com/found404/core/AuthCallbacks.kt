package com.found404.core

interface AuthCallbacks<AuthResponseType> {
    fun onSuccessfulLogin(response: AuthResponseType)
    fun onFailedLogin(response: AuthResponseType)
    fun onServerUnreachable(error: Throwable)
}