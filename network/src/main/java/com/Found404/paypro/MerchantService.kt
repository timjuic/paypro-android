package com.Found404.paypro

import responses.ApiResponse
import responses.Merchant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception



class MerchantService {
    private val gson = Gson()
    private val client = OkHttpClient()


    suspend fun getMerchantsForUser(userId: String): List<Merchant>? = withContext(Dispatchers.IO) {
        val url = "http://158.220.113.254:8086/api/merchant/$userId"
        val jwtToken: String = "your_jwt_token_here"

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $jwtToken")
            .get()
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            // Assuming the response contains a JSON array of merchants
            val merchantListType = object : TypeToken<List<Merchant>>() {}.type
            gson.fromJson<List<Merchant>>(responseBody, merchantListType)
        } catch (e: Exception) {
            // Handle exceptions appropriately
            // For instance, returning null or an empty list in case of failure
            null
        }
    }
    suspend fun deletingMerchantById(merchant: Merchant): Boolean? = withContext(Dispatchers.IO){
        val baseUrl = "http://158.220.113.254:8086/api/merchant/"
        val jwtToken: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXRpamFrbGphaWNAZ21haWwuY29tIiwiZXhwIjoxNzAyNzQ0NTA1LCJpYXQiOjE3MDI3NDI3MDUsImlzX2FkbWluIjpmYWxzZSwibGFzdF9uYW1lIjoiS2xqYWljIiwiaWQiOiIyNiIsImZpcnN0X25hbWUiOiJNYXRpamEifQ.taf9iOaTObEZaRqSwHzH0vcAUbj5s6B2krIKHO1s6a4"
        val urlString = "$baseUrl${merchant.id}"

        val request = Request.Builder()
            .url(urlString)
            .header("Authorization", "Bearer $jwtToken")
            .delete()
            .build()
        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            val result = gson.fromJson(responseBody,ApiResponse::class.java)
            println(result.success.toString() + " " + result.message + " " + result.data)


            result.success
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}
