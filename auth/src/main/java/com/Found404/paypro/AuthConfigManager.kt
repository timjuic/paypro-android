package com.Found404.paypro

import com.found404.core.auth.AuthConfig

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