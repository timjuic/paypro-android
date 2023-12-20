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
        merchantStreetNumber: Int,
        acceptedCards: List<String>,
        status: String
    ) : AddingMerchantsResult
}