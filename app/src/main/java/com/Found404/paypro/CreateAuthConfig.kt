package com.Found404.paypro

fun createAuthService(baseUrl: String): AuthServiceImpl {
    val authConfig = AuthConfig.create().apply {
        this.baseUrl = baseUrl
    }

    return AuthServiceImpl(authConfig)
}
