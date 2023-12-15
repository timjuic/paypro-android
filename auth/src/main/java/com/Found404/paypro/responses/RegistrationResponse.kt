package com.Found404.paypro.responses

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("errorCode") val errorCode: Int? = null,
    @SerializedName("errorMessage") val errorMessage: String? = null,
    @SerializedName("error") val error: String? = null
)