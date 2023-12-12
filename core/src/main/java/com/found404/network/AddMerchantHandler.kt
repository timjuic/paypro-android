package com.found404.network

interface AddMerchantHandler {

    fun addMerchantName(fullName: String)

    fun addMerchantAddress(cityName: String, streetName: String, postCode: Int, streetNumber: Int)

    fun merchantCard()
}

