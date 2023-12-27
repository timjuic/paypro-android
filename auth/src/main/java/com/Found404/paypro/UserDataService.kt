package com.Found404.paypro

import android.content.Context
import com.Found404.paypro.responses.LoginData

interface UserDataService {
    fun saveLoggedInUser(loginData: LoginData?, context: Context)

    fun getLoggedInUser(context: Context): UserData

    fun logoutUser(context: Context)

    fun getAuthToken(context: Context): String?
}