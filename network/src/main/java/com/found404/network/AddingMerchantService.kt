package com.found404.network

interface AddingMerchantService {
    suspend fun addMerchant(merchantName: String,
                             merchantStreetName: String,
                             merchantCityName: String,
                             merchantPostCode: Int,
                             merchantStreetNumber: Int,
                            acceptedCards: List<String>
    ) : AddingMerchantsResult
}