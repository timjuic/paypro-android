package com.found404.network.service

import android.content.Context
import com.found404.network.result.AddingMerchantsResult

interface AddingMerchantService {
    suspend fun addMerchant(
        context: Context,
        merchantName: String,
        merchantStreetName: String,
        merchantCityName: String,
        merchantPostCode: Int,
        merchantStreetNumber: String,
        acceptedCards: List<Map<String, Any>>,
        status: String
    ) : AddingMerchantsResult
}