package com.found404.network.service

import android.content.Context
import com.found404.core.models.CreditCardType
import com.found404.createAuthService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import responses.ApiResponse

class CreditCardsService {
    private val gson = Gson()
    private val client = OkHttpClient()
    private val authService = createAuthService("http://158.220.113.254:8086")

    suspend fun getCreditCardTypes(context: Context): List<CreditCardType>? = withContext(Dispatchers.IO) {
        val url = "http://158.220.113.254:8086/api/card-brands"
        val jwtToken = authService.getAuthToken(context)

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $jwtToken")
            .get()
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            println("responseBody " + responseBody)
            val type = object : TypeToken<ApiResponse<List<CreditCardType>>>() {}.type

            val result = gson.fromJson<ApiResponse<List<CreditCardType>>>(responseBody, type)

            if (result.success) {
                result.data
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
