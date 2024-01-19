package com.found404.paypro.login_email_password.parser

import com.google.gson.Gson

class GsonJsonParser : JsonParser {
    private val gson = Gson()
    override fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

    override fun <T> toJson(obj: T): String {
        return gson.toJson(obj)
    }
}