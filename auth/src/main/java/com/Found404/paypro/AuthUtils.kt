package com.Found404.paypro

import java.security.MessageDigest

class AuthUtils {
    companion object {
        @JvmStatic
        fun hashPassword(password: String): String {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashBytes = messageDigest.digest(password.toByteArray())
            return hashBytes.joinToString("") { "%02x".format(it) }
        }
    }
}