package com.Found404.paypro

import com.found404.core.AppConfig
import com.found404.core.AuthConfig

class AuthDependencyProvider private constructor() {

    private val authConfig = AuthConfig.create().apply {
        this.baseUrl = AppConfig.BASE_URL
    }

    private val authService = AuthServiceImpl(authConfig)

    companion object {
        private var instance: AuthDependencyProvider? = null

        fun getInstance(): AuthDependencyProvider {
            return instance ?: synchronized(this) {
                instance ?: AuthDependencyProvider().also { instance = it }
            }
        }
    }

    fun getAuthService(): AuthServiceImpl {
        return authService
    }
}
