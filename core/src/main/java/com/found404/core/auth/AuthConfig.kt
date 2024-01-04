package com.found404.core.auth

class AuthConfig private constructor(
    var baseUrl: String = "http://default_server_ip:8086",
) {
    companion object {
        fun create(): AuthConfig {
            return AuthConfig()
        }
    }
}