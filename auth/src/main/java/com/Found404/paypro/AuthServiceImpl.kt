package com.Found404.paypro


import android.content.Context
import com.Found404.paypro.responses.LoginData
import com.Found404.paypro.responses.LoginResponse
import com.Found404.paypro.responses.RegistrationResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class AuthServiceImpl(
    private val authConfig: AuthConfig
) : AuthService {
    val validator: AuthValidator = AuthValidator()
    private val gson = Gson()
    private val client = OkHttpClient()

    override suspend fun registerUser(
        endpointPath: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): RegistrationResponse = withContext(Dispatchers.IO) {
        if (!validator.validateAll(firstName, lastName, email, password).success) {
            return@withContext RegistrationResponse(false, "Validation failed")
        }

        val hashedPassword = AuthUtils.hashPassword(password)

        val requestBody = gson.toJson(
            mapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "emailAddress" to email,
                "password" to hashedPassword,
                "repeatedPassword" to hashedPassword,
            )
        ).toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("${authConfig.baseUrl}${endpointPath}")
            .post(requestBody)
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            gson.fromJson(responseBody, RegistrationResponse::class.java)
        } catch (e: Exception) {
            RegistrationResponse(false, "Registration failed", error = e.message)
        }
    }


    override suspend fun loginUser(
        endpointPath: String,
        email: String,
        password: String,
        context: Context
    ): LoginResponse = withContext(Dispatchers.IO) {
        val hashedPassword = AuthUtils.hashPassword(password)

        val requestBody = gson.toJson(
            mapOf(
                "emailAddress" to email,
                "password" to hashedPassword
            )
        ).toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("${authConfig.baseUrl}${endpointPath}")
            .post(requestBody)
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val loginResponse = gson.fromJson(responseBody, LoginResponse::class.java)
            if (loginResponse.success) {
                saveLoggedInUser(loginResponse.data, context)
            }

            loginResponse
        } catch (e: Exception) {
            LoginResponse(false, "Login failed", error = e.message)
        }
    }


    private fun saveLoggedInUser(loginData: LoginData?, context: Context) {
        loginData?.let { data ->
            val jwtToken = data.accessToken
            val refreshToken = data.refreshToken.token

            val jwtParser = JWT.decode(jwtToken)
            val userId = jwtParser.claims["id"]?.asString()
            val userEmail = jwtParser.claims["sub"]?.asString()

            val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("user_id", userId)
            editor.putString("user_email", userEmail)
            editor.putString("jwt_token", jwtToken)
            editor.putString("refresh_token", refreshToken)

            editor.apply()
        }
    }


    private fun getLoggedInUser(context: Context): UserData {
        val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)

        val userId = sharedPreferences.getString("user_id", null)
        val userEmail = sharedPreferences.getString("user_email", null)
        val jwtToken = sharedPreferences.getString("jwt_token", null)
        val refreshToken = sharedPreferences.getString("refresh_token", null)

        return UserData(userId, userEmail, jwtToken, refreshToken)
    }


    fun getAuthToken(context: Context): String? {
        val loggedInUser = getLoggedInUser(context)
        return loggedInUser.jwtToken
    }


    suspend fun isJwtValid(context: Context): Boolean {
        val userData = getLoggedInUser(context)

        if (userData.userId == null || userData.userEmail == null || userData.refreshToken == null) {
            return false
        }

        return userData.jwtToken?.let { jwtString ->
            try {
                val jwtParser = JWT.decode(jwtString)
                val expirationTimestamp = jwtParser.expiresAt.time
                val currentTime = System.currentTimeMillis()

                if (currentTime <= expirationTimestamp) {
                    // Access token is still valid
                    true
                } else {
                    // Access token is expired, try to refresh it
                    val refreshedToken = refreshAccessToken("/api/auth/refresh-token", userData.refreshToken)
                    if (refreshedToken != null) {
                        // Save the refreshed access token
                        saveAccessToken(refreshedToken, context)
                        true
                    } else {
                        // Access token couldnt be refreshed
                        false
                    }
                }
            } catch (e: JWTDecodeException) {
                false
            }
        } ?: false
    }


    private fun saveAccessToken(accessToken: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("jwt_token", accessToken)
        editor.apply()
    }


    override suspend fun refreshAccessToken(endpointPath: String, refreshToken: String): String? = withContext(Dispatchers.IO) {
        val url = "${authConfig.baseUrl}${endpointPath}"
        val requestBody = FormBody.Builder()
            .add("refreshToken", refreshToken)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        try {
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                gson.fromJson(responseBody, LoginData::class.java).accessToken
            } else {
                null
            }
        } catch (e: IOException) {
            null
        }
    }
}