package com.Found404.paypro

import com.google.gson.Gson
import okhttp3.OkHttpClient

object DependencyProvider {
    val gson: Gson by lazy { Gson() }
    val client: OkHttpClient by lazy { OkHttpClient() }
}