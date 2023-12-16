package com.Found404.paypro


import android.content.Context
import com.Found404.paypro.responses.LoginResponse
import com.Found404.paypro.responses.RegistrationResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class AuthServiceImpl : AuthService {
    val validator: AuthValidator = AuthValidator()
    private val gson = Gson()
    private val client = OkHttpClient()

    override suspend fun registerUser(
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
            .url("http://158.220.113.254:8086/api/auth/register")
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

    override suspend fun loginUser(email: String, password: String, context: Context): LoginResponse = withContext(Dispatchers.IO) {
//        if (!validator.validateLogin(email, password).success) {
//            return@withContext LoginResponse(false, "Validation failed")
//        }

        val hashedPassword = AuthUtils.hashPassword(password)

        val requestBody = gson.toJson(
            mapOf(
                "emailAddress" to email,
                "password" to hashedPassword
            )
        ).toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("http://158.220.113.254:8086/api/auth/login")
            .post(requestBody)
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val loginResponse = gson.fromJson(responseBody, LoginResponse::class.java)
            if (loginResponse.success) {
                saveLoggedInUser(loginResponse.data!!.accessToken, email, context)
            }

            loginResponse
        } catch (e: Exception) {
            LoginResponse(false, "Login failed", error = e.message)
        }
    }

    private fun saveLoggedInUser(jwt: String?, email: String, context: Context) {
        jwt?.let {
            val jwtParser = JWT.decode(it)
            val userId = jwtParser.claims["id"]?.asString()

            val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("user_id", userId)
            editor.putString("user_email", email)
            editor.putString("jwt_token", it)

            editor.apply()
        }
    }

    fun getLoggedInUser(context: Context): Pair<String?, String?> {
        val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)

        val userId = sharedPreferences.getString("user_id", null)
        val userEmail = sharedPreferences.getString("user_email", null)

        return Pair(userId, userEmail)
    }

    fun isJwtValid(context: Context): Boolean {
        val (userId, userEmail) = getLoggedInUser(context)

        if (userId == null || userEmail == null) {
            return false
        }

        val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val jwt = sharedPreferences.getString("jwt_token", null)

        return jwt?.let { jwtString ->
            try {
                val jwtParser = JWT.decode(jwtString)
                val expirationTimestamp = jwtParser.expiresAt.time
                val currentTime = System.currentTimeMillis()

                currentTime <= expirationTimestamp
            } catch (e: JWTDecodeException) {
                // Handle JWT decoding error
                false
            }
        } ?: false
    }
}