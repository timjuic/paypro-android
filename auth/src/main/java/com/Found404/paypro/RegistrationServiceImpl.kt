package com.Found404.paypro


import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class RegistrationServiceImpl : RegistrationService {
    val validator: RegistrationValidator = RegistrationValidator()
    private val gson = Gson()
    private val client = OkHttpClient()

    override suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): RegistrationResult = withContext(Dispatchers.IO) {
        if (!validator.validateAll(firstName, lastName, email, password).success) {
            return@withContext RegistrationResult(false, "Validation failed")
        }

        val requestBody = gson.toJson(
            mapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "emailAddress" to email,
                "password" to password,
                "repeatedPassword" to password,
            )
        ).toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("http://158.220.113.254:8086/api/auth/register")
            .post(requestBody)
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val result = gson.fromJson(responseBody, RegistrationResponse::class.java)
            RegistrationResult(result.success, result.message, result.errorCode, result.errorMessage)
        } catch (e: Exception) {
            RegistrationResult(false, "Registration failed", error = e.message)
        }
    }

}

data class RegistrationResponse(
    val success: Boolean,
    val message: String,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)
