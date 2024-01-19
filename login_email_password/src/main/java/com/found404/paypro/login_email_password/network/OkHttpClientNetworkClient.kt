package com.found404.paypro.login_email_password.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpClientNetworkClient : NetworkClient {
    private val client = OkHttpClient()
    override suspend fun performRequest(request: Request): String? {
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute().body?.string()
        }
    }
}