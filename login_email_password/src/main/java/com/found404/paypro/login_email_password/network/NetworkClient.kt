package com.found404.paypro.login_email_password.network

import okhttp3.Request

interface NetworkClient {
    suspend fun performRequest(request: Request): String?
}