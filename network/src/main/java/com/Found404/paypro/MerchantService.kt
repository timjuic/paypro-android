package com.Found404.paypro

import responses.ApiResponse
import responses.Merchant
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

/*data class ApiResponse(
    val success: Boolean,
    val message: String,
    val errorCode: Int?,
    val errorMessage: String?,
    val data: List<Merchant>
)*/

class MerchantService {
    private val gson = Gson()
    private val client = OkHttpClient()

    suspend fun getMerchantForUser(userId: String): List<Merchant>? = withContext(Dispatchers.IO){
        var url ="http://158.220.113.254:8086/api/merchant/$userId"
        val jwtToken: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXRpamFrbGphaWNAZ21haWwuY29tIiwiZXhwIjoxNzAyNjc1Njc5LCJpYXQiOjE3MDI2NzM4NzksImlzX2FkbWluIjpmYWxzZSwibGFzdF9uYW1lIjoiS2xqYWljIiwiaWQiOiIyNiIsImZpcnN0X25hbWUiOiJNYXRpamEifQ.DE5aPVyur3eiAomehYgXbGYm5f08I5cNAeYxjjkkVhM"


                val request = Request.Builder()
                    .url(url)
                    .header("Authorization", "Bearer $jwtToken")
                    .get()
                    .build()
                return@withContext try {
                    val response = client.newCall(request).execute()
                    val responseBody = response.body?.string()
                    val result = gson.fromJson(responseBody,ApiResponse::class.java)
                    println(result.success.toString() + " " + result.message + " " + result.data)
                    //ApiResponse(result.success,result.message,result.errorCode,result.errorMessage,result.data)

                    result.data
                    }catch(e:Exception){
                        e.printStackTrace()
                        null
                    }

        }
    }
