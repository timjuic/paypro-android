package com.Found404.paypro.responses

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: RefreshToken
)

data class RefreshToken(
    @SerializedName("token") val token: String,
    @SerializedName("valid_for") val validity: Validity
)

data class Validity(
    @SerializedName("time_unit") val timeUnit: String,
    @SerializedName("time_amount") val timeAmount: Int
)