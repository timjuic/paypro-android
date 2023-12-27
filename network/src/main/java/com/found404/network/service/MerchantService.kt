package com.found404.network.service

import android.content.Context
import com.Found404.paypro.AuthServiceImpl
import com.found404.core.models.Terminal
import com.found404.createAuthService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MerchantService {
    private val gson = Gson()
    private val client = OkHttpClient()
    private val authService = createAuthService("http://158.220.113.254:8086")

    suspend fun getMerchantsForUser(context: Context): List<Merchant>? = withContext(Dispatchers.IO) {
        val user = AuthServiceImpl.getLoggedInUser(context)
        val userID = user.userId
        println("userID " + userID)
        val url = "http://158.220.113.254:8086/api/merchant/$userID" // needs mid not user id
        val jwtToken = authService.getAuthToken(context)

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $jwtToken")
            .get()
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            println("responseBody MerchantsForUser  " + responseBody) //merchants with specified id couldn't be found
            val merchantListType = object : TypeToken<List<Merchant>>() {}.type
            gson.fromJson<List<Merchant>>(responseBody, merchantListType)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getTerminalsForAllMerchants(context: Context): List<Terminal>? = withContext(Dispatchers.IO) {
        val merchants = getMerchantsForUser(context) ?: return@withContext emptyList()
        merchants.mapNotNull { merchant ->
            getTerminalsForMerchant(merchant.id, context)
        }.flatten()
    }

    suspend fun getTerminalsForMerchant(merchantId: String, context: Context): List<Terminal>? {
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
            println("responseBody  TerminalsForMerchant  " + responseBody)
            val type = object : TypeToken<List<Terminal>>() {}.type
            gson.fromJson(responseBody, type)
        } catch (e: Exception) {
            null
        }
    }
}


data class Merchant(
    val id: String,
    val fullName: String,
    val cityName: String,
    val streetName: String,
    val postCode: Int,
    val streetNumber: Int
)
