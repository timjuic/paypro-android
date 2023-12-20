package com.found404

import com.Found404.paypro.AuthConfig
import com.Found404.paypro.AuthServiceImpl

fun createAuthService(baseUrl: String): AuthServiceImpl {
    val authConfig = AuthConfig.create().apply {
        this.baseUrl = baseUrl
    }

    return AuthServiceImpl(authConfig)
}
