package com.found404.network.service

import android.content.Context
import com.Found404.paypro.AuthDependencyProvider
import com.Found404.paypro.responses.RegistrationResponse
import com.found404.core.models.MerchantEditResponse
import com.found404.core.models.MerchantResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import responses.ApiResponse

class MerchantService {
    private val gson = Gson()
    private val client = OkHttpClient()
    private val dependencyProvider = AuthDependencyProvider.getInstance()
    private val authService = dependencyProvider.getAuthService()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    fun getMerchantsForUser(context: Context, callback: (List<MerchantResponse>?, String?) -> Unit) {
        coroutineScope.launch {
            try {
                val user = authService.getLoggedInUser(context)
                val userID = user.userId
                val url = "http://158.220.113.254:8086/api/merchant/${userID}"
                val jwtToken = authService.getAuthToken(context)

                val request = Request.Builder()
                    .url(url)
                    .header("Authorization", "Bearer $jwtToken")
                    .get()
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                val apiResponseType = object : TypeToken<ApiResponse<List<MerchantResponse>>>() {}.type
                val apiResponse = gson.fromJson<ApiResponse<List<MerchantResponse>>>(responseBody, apiResponseType)

                withContext(Dispatchers.Main) {
                    if (apiResponse.success) {
                        callback(apiResponse.data, null)
                    } else {
                        callback(null, apiResponse.errorMessage)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback(null, e.message)
                }
            }
        }
    }


    suspend fun deleteTerminal(merchantId: Int, terminalId: Int, context: Context): ApiResponse<Unit>? = withContext(Dispatchers.IO) {
        val url = "http://158.220.113.254:8086/api/merchant/${merchantId}/terminal/${terminalId}"
        val jwtToken = authService.getAuthToken(context)

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $jwtToken")
            .delete()
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val apiResponseType = object : TypeToken<ApiResponse<Unit>>() {}.type
            gson.fromJson<ApiResponse<Unit>>(responseBody, apiResponseType).also {
                if (!response.isSuccessful) {
                    println("Error: ${it.errorMessage}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteMerchant(merchantId: Int, context: Context): RegistrationResponse? = withContext(Dispatchers.IO) {
        val url = "http://158.220.113.254:8086/api/merchant/$merchantId"
        println("Postoji merchant sa ID" + merchantId)
        val jwtToken = authService.getAuthToken(context)

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $jwtToken")
            .delete()
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            gson.fromJson(responseBody, RegistrationResponse::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    suspend fun editMerchant(
        context: Context,
        merchantId: Int,
        merchantName: String,
        merchantStreetName: String,
        merchantCityName: String,
        merchantPostCode: Int,
        merchantStreetNumber: Int,
        acceptedCards: List<Map<String, Any>>,
        statusFlag: Int
    ): MerchantEditResponse = withContext(Dispatchers.IO) {
        val (statusId, statusName) = when (statusFlag) {
            1 -> "1" to "Active"
            2 -> "2" to "Disabled"
            else -> "1" to "Active"
        }

        val url = "http://158.220.113.254:8086/api/merchant/${merchantId}"
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
                "status" to mapOf(
                    "statusId" to statusId,
                    "statusName" to statusName
                )
            )
        ).toRequestBody("application/json".toMediaType())

        val jwtToken = authService.getAuthToken(context)

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $jwtToken")
            .put(requestBody)
            .build()
        println("request  " + request)
        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val result = gson.fromJson(responseBody, MerchantEditResponse::class.java)
            MerchantEditResponse(result.success, result.message, result.errorCode, result.errorMessage)
        } catch (e: Exception) {
            MerchantEditResponse(false, "Editing merchant failed", error = e.message)
        }
    }

}

