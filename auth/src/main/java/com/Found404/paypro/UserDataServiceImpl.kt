package com.Found404.paypro

import android.content.Context
import com.auth0.jwt.JWT
import com.found404.core.auth.LoginData


class UserDataServiceImpl : UserDataService {
    override fun saveLoggedInUser(loginData: LoginData?, context: Context) {
        loginData?.let { data ->
            val jwtToken = data.accessToken
            val refreshToken = data.refreshToken.token

            val jwtParser = JWT.decode(jwtToken)
            val userId = jwtParser.claims["id"]?.asString()
            val userEmail = jwtParser.claims["sub"]?.asString()
            val firstName = jwtParser.claims["first_name"]?.asString()
            val lastName = jwtParser.claims["last_name"]?.asString()

            val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("user_id", userId)
            editor.putString("user_email", userEmail)
            editor.putString("jwt_token", jwtToken)
            editor.putString("refresh_token", refreshToken)
            editor.putString("first_name", firstName)
            editor.putString("last_name", lastName)

            editor.apply()
        }
    }

    override fun getLoggedInUser(context: Context): UserData {
        val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)

        val userId = sharedPreferences.getString("user_id", null)
        val firstName = sharedPreferences.getString("first_name", null)
        val lastName = sharedPreferences.getString("last_name", null)
        val userEmail = sharedPreferences.getString("user_email", null)
        val jwtToken = sharedPreferences.getString("jwt_token", null)
        val refreshToken = sharedPreferences.getString("refresh_token", null)

        return UserData(userId, firstName, lastName, userEmail, jwtToken, refreshToken)
    }

    override fun logoutUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove("user_id")
        editor.remove("first_name")
        editor.remove("last_name")
        editor.remove("user_email")
        editor.remove("jwt_token")
        editor.remove("refresh_token")

        editor.apply()
    }

    override fun getAuthToken(context: Context): String? {
        val loggedInUser = this.getLoggedInUser(context)
        return loggedInUser.jwtToken
    }

    override fun saveAccessToken(accessToken: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("jwt_token", accessToken)
        editor.apply()
    }
}