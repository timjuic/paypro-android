package com.found404.network.service

import android.content.Context
import com.Found404.paypro.AuthDependencyProvider
import com.found404.core.models.MerchantResponse
import com.found404.core.models.Terminal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import responses.ApiResponse

class MerchantService {
    private val gson = Gson()
    private val client = OkHttpClient()
    private val authService = AuthDependencyProvider.getInstance().getAuthService()

    suspend fun getMerchantsForUser(context: Context): List<MerchantResponse>? = withContext(Dispatchers.IO) {
        val user = authService.getLoggedInUser(context)
        val userID = user.userId
        val url = "http://158.220.113.254:8086/api/merchant/$userID"
        val jwtToken = authService.getAuthToken(context)

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $jwtToken")
            .get()
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val apiResponseType = object : TypeToken<ApiResponse<List<MerchantResponse>>>() {}.type
            val apiResponse = gson.fromJson<ApiResponse<List<MerchantResponse>>>(responseBody, apiResponseType)
            if (apiResponse.success) {
                apiResponse.data
            } else {
                println("Error: ${apiResponse.errorMessage}")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    suspend fun getTerminalsForAllMerchants(context: Context): List<Terminal>? = withContext(Dispatchers.IO) {
        val merchants = getMerchantsForUser(context) ?: return@withContext emptyList()
        merchants.mapNotNull { merchant ->
            getTerminalsForMerchant(merchant.merchantId.toString(), context)
        }.flatten()
    }

    private suspend fun getTerminalsForMerchant(merchantId: String, context: Context): List<Terminal>? {
        val url = "http://158.220.113.254:8086/api/merchant/$merchantId/terminal"
        val jwtToken = authService.getAuthToken(context)

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $jwtToken")
            .get()
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return null
            val responseBody = response.body?.string()
            val type = object : TypeToken<List<Terminal>>() {}.type
            gson.fromJson(responseBody, type)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteTerminal(merchantId: Int, terminalId: String, context: Context): ApiResponse<Unit>? = withContext(Dispatchers.IO) {
        val url = "http://158.220.113.254:8086/api/merchant/$merchantId/terminal/$terminalId"
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
}

