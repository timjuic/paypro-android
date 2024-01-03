package com.Found404.paypro

import android.content.Context
import com.Found404.paypro.AuthConfigHolder.authConfig
import com.Found404.paypro.DependencyProvider.client
import com.Found404.paypro.DependencyProvider.gson
import com.Found404.paypro.responses.RegistrationResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.found404.core.auth.LoginData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class JWTAuthStrategyImpl(
    private val userDataService: UserDataService,
) : JWTAuthStrategy {


    override suspend fun registerUser(
        endpointPath: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): RegistrationResponse = withContext(Dispatchers.IO) {
        if (!AuthValidator.validateAll(firstName, lastName, email, password).success) {
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

    override suspend fun isJwtValid(context: Context): Boolean {
        val userData = userDataService.getLoggedInUser(context)

        if (userData.userId == null || userData.email == null || userData.refreshToken == null) {
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
                        userDataService.saveAccessToken(refreshedToken, context)
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