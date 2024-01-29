package com.found404.paypro.login_email_password.auth

import java.security.MessageDigest

class LoginUtils {
    companion object {
        @JvmStatic
        fun hashPassword(password: String): String {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashBytes = messageDigest.digest(password.toByteArray())
            return hashBytes.joinToString("") { "%02x".format(it) }
        }
    }
}