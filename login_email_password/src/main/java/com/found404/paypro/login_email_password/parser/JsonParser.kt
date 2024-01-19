package com.found404.paypro.login_email_password.parser

interface JsonParser {
    fun <T> fromJson(json: String, classOfT: Class<T>): T

    fun <T> toJson(obj: T): String
}