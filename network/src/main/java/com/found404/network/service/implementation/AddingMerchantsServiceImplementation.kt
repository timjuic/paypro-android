package com.found404.network.service.implementation

import android.content.Context
import com.Found404.paypro.AuthDependencyProvider
import com.found404.network.result.AddingMerchantsResult
import com.found404.network.service.AddingMerchantService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class AddingMerchantsServiceImplementation : AddingMerchantService {
    private val gson = Gson()
    private val client = OkHttpClient()

    private val authService = AuthDependencyProvider.getInstance().getAuthService()

    override suspend fun addMerchant(
        context: Context,
        merchantName: String,
        merchantStreetName: String,
        merchantCityName: String,
        merchantPostCode: Int,
        merchantStreetNumber: String,
        acceptedCards: List<Map<String, Any>>,
        status: String
    ): AddingMerchantsResult = withContext(Dispatchers.IO) {

        val currentUser = authService.getLoggedInUser(context)

        val requestBody = gson.toJson(
            mapOf(
                "merchantName" to merchantName,
                "address" to mapOf(
                    "city" to merchantCityName,
                    "streetName" to merchantStreetName,
                    "streetNumber" to merchantStreetNumber.toString(),
                    "postalCode" to merchantPostCode.toString()
                ),
                "acceptedCards" to acceptedCards,
                "status" to mapOf("statusId" to 1, "statusName" to "Active")
            )
        ).toRequestBody("application/json".toMediaType())

        val jwtToken = authService.getAuthToken(context)
        val request = Request.Builder()
            .url("http://158.220.113.254:8086/api/merchant/${currentUser.userId}")
            .header("Authorization", "Bearer $jwtToken")
            .post(requestBody)
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val result = gson.fromJson(responseBody, AddingMerchantsResponse::class.java)
            AddingMerchantsResult(result.success, result.message, result.errorCode, result.errorMessage)
        } catch (e: Exception) {
            AddingMerchantsResult(false, "Adding new merchant failed", error = e.message)
        }
    }
}

data class AddingMerchantsResponse(
    val success: Boolean,
    val message: String,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)