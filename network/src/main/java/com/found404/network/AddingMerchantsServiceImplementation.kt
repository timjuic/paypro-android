package com.found404.network

import com.found404.ValidationLogic.MerchantDataValidator
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class AddingMerchantsServiceImplementation : AddingMerchantService{
    private val validator = MerchantDataValidator()
    private val gson = Gson()
    private val client = OkHttpClient()

    override suspend fun registerUser(
        merchantName: String,
        merchantStreetName: String,
        merchantCityName: String,
        merchantPostCode: Int,
        merchantStreetNumber: Int
    ): AddingMerchantsResult  = withContext(Dispatchers.IO) {
        if (!validator.validateAll(merchantName, merchantStreetName, merchantCityName, merchantPostCode, merchantStreetNumber).success) {
            return@withContext AddingMerchantsResult(false, "Validation failed")
        }

        val requestBody = gson.toJson(
            mapOf(
                "fullName" to merchantName,
                "streetName" to merchantStreetName,
                "cityName" to merchantCityName,
                "postCode" to merchantPostCode,
                "streetNumber" to merchantStreetNumber,
            )
        ).toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("http://158.220.113.254:8086/api/merchant")
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