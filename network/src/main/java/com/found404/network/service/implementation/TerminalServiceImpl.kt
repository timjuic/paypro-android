package com.found404.network.service.implementation

import android.content.Context
import com.found404.core.models.Terminal
import com.found404.createAuthService
import com.found404.network.result.AddingTerminalResult
import com.found404.network.service.TerminalService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class TerminalServiceImpl(context: Context): TerminalService {
    private val client = OkHttpClient()
    private val authClient = createAuthService("http://158.220.113.254:8086")
    private val tempJwt = authClient.getAuthToken(context)

    override suspend fun addTerminal(terminal: Terminal, mid: Int): AddingTerminalResult = withContext(Dispatchers.IO) {
        val requestBody = createRequestBody(terminal)
        val request = Request.Builder()
            .url("http://158.220.113.254:8086/api/merchant/$mid/terminal")
            .header("Authorization", "Bearer $tempJwt")
            .post(requestBody)
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            convertJsonToResponse(responseBody.toString())
        } catch (e: Exception) {
            AddingTerminalResult(false, "Adding new terminal failed", error = e.message)
        }
    }

    private fun createRequestBody(terminal: Terminal): RequestBody {
        val gson = Gson()
        val jsonString = gson.toJson(terminal)
        return jsonString.toRequestBody("application/json".toMediaType())
    }

    private fun convertJsonToResponse(jsonString: String): AddingTerminalResult {
        val gson = Gson()
        return gson.fromJson(jsonString, AddingTerminalResult::class.java)
    }
}