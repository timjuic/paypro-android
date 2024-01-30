package com.found404.paypro.login_google.parser

interface JsonParser {
    fun <T> fromJson(json: String, classOfT: Class<T>): T
}