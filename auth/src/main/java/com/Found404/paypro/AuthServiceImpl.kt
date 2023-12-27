package com.Found404.paypro


import com.google.gson.Gson
import okhttp3.OkHttpClient

class AuthServiceImpl(
    private val authConfig: AuthConfig
) {
    private val gson = Gson()
    private val client = OkHttpClient()

    init {
        AuthConfigManager.initialize(authConfig)
    }

}