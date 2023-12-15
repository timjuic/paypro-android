package com.found404.network

interface AddingMerchantService {
    suspend fun registerUser(merchantName: String,
                             merchantStreetName: String,
                             merchantCityName: String,
                             merchantPostCode: Int,
                             merchantStreetNumber: Int
    ) : AddingMerchantsResult
}