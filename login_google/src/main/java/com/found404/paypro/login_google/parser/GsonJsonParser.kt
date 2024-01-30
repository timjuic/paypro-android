package com.found404.paypro.login_google.parser

import com.google.gson.Gson

class GsonJsonParser : JsonParser {
    private val gson = Gson()
    override fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }
}