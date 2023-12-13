package com.found404.core.models

data class Merchant (
    var fullName: String = "",
    var cityName: String = "",
    var streetName: String = "",
    var postCode: Int = 0,
    var streetNumber: Int = 0,
    var acceptedCards: List<String> = listOf("")
)
