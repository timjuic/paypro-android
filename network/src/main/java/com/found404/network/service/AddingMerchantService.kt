package com.found404.network.service

import com.found404.network.result.AddingMerchantsResult

interface AddingMerchantService {
    suspend fun addMerchant(
        merchantName: String,
        merchantStreetName: String,
        merchantCityName: String,
        merchantPostCode: Int,
        merchantStreetNumber: Int,
        acceptedCards: List<String>,
        status: String
    ) : AddingMerchantsResult
}