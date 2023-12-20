package com.found404.network.service

import com.found404.core.models.CreditCardType
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

    suspend fun getCreditCardTypes(): List<CreditCardType>? = withContext(Dispatchers.IO) {
        val url = "http://158.220.113.254:8086/api/card-brands"
        val jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXRpamFrbGphaWNAZ21haWwuY29tIiwiZXhwIjoxNzAyNzM5MTAxLCJpYXQiOjE3MDI3MzczMDEsImlzX2FkbWluIjpmYWxzZSwibGFzdF9uYW1lIjoiS2xqYWljIiwiaWQiOiIyNiIsImZpcnN0X25hbWUiOiJNYXRpamEifQ.INvzWktskeYNqvxTH-y7Gs7gzAc17ejsamv2Vij9Tp4"

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $jwtToken")
            .get()
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

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
