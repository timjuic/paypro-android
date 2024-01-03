package com.Found404.paypro

import android.content.Context
import com.found404.core.auth.LoginData

interface UserDataService {
    fun saveLoggedInUser(loginData: LoginData?, context: Context)

    fun getLoggedInUser(context: Context): UserData

    fun logoutUser(context: Context)

    fun getAuthToken(context: Context): String?

    fun saveAccessToken(accessToken: String, context: Context)
}