package com.Found404.paypro

class AuthConfigManager {

    companion object {
        fun initialize(authConfig: AuthConfig) {
            AuthConfigHolder.authConfig = authConfig
        }
    }
}

object AuthConfigHolder {
    lateinit var authConfig: AuthConfig
}