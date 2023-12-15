package com.found404.network

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class AddingMerchantsServiceImplementation : AddingMerchantService{
    private val gson = Gson()
    private val client = OkHttpClient()

    override suspend fun addMerchant(
        merchantName: String,
        merchantStreetName: String,
        merchantCityName: String,
        merchantPostCode: Int,
        merchantStreetNumber: Int,
    ): AddingMerchantsResult  = withContext(Dispatchers.IO) {

        val requestBody = gson.toJson(
            mapOf(
                "merchantName" to merchantName,
                "address" to mapOf(
                    "city" to merchantCityName,
                    "streetName" to merchantStreetName,
                    "streetNumber" to merchantStreetNumber.toString(),
                    "postalCode" to merchantPostCode.toString()
                )
            )
        ).toString().toRequestBody("application/json".toMediaType())
        println("requestbody " + gson.toJson(
            mapOf(
                "merchantName" to merchantName,
                "address" to mapOf(
                    "city" to merchantCityName,
                    "streetName" to merchantStreetName,
                    "streetNumber" to merchantStreetNumber.toString(),
                    "postalCode" to merchantPostCode.toString()
                )
            )
        ).toString())
        val jwtToken: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXRpamFrbGphaWNAZ21haWwuY29tIiwiZXhwIjoxNzAyNjc0NzY0LCJpYXQiOjE3MDI2NzI5NjQsImlzX2FkbWluIjpmYWxzZSwibGFzdF9uYW1lIjoiS2xqYWljIiwiaWQiOiIyNiIsImZpcnN0X25hbWUiOiJNYXRpamEifQ.Md0IfFYci0-vkg9_T_iSCD4I-bPxQCQvm33BrnTvKck"
        val request = Request.Builder()
            .url("http://158.220.113.254:8086/api/merchant")
            .header("Authorization", "Bearer $jwtToken")
            .post(requestBody)
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val result = gson.fromJson(responseBody, AddingMerchantsResponse::class.java)
            println("success " + result.success)
            println("message " + result.message)
            println("errorCode " + result.errorCode)
            println("errorMessage " + result.errorMessage)
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