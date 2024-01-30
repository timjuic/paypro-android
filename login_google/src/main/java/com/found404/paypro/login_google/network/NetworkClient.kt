package com.found404.paypro.login_google.network

import okhttp3.Request

interface NetworkClient {
    suspend fun performRequest(request: Request): String?
}