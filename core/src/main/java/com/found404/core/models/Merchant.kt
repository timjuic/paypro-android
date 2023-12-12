package com.found404.core.models

import java.util.Date

data class Merchant (
    val merchantID: Int?,
    val fullName: String?,
    val status: Int?,
    val cityName: String?,
    val streetName: String?,
    val postCode: Int?,
    val streetNumber: Int?,
    val dateCreated: Date?
)
