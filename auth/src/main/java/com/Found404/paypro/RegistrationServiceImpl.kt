package com.Found404.paypro


import com.Found404.paypro.responses.RegistrationResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class RegistrationServiceImpl : RegistrationService {
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
}