package responses

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("errorCode") val errorCode: Int? = null,
    @SerializedName("errorMessage") val errorMessage: String? = null,
    @SerializedName("data") val data: List<Merchant>? = null
)

data class Merchant(
    @SerializedName("id") val id: Int,
    @SerializedName("merchantName") val merchantName: String,
    @SerializedName("address") val address: Address,
    @SerializedName("acceptedCards") val acceptedCards: List<String>? = null,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("status") val status: Status,
    //@SerializedName("terminals") val terminals: List<Terminal>? = null,
    //@SerializedName("userAccounts") val userAccounts: List<UserAccount>? = null
)

data class Address(
    @SerializedName("streetName") val streetName: String,
    @SerializedName("city") val city: String,
    @SerializedName("streetNumber") val streetNumber: String,
    @SerializedName("postalCode") val postalCode: Int
)

data class Status(
    @SerializedName("statusId") val statusId: Int,
    @SerializedName("statusName") val statusName: String
)

/*data class Terminal(
    // Define properties for Terminal if available in your JSON
)

data class UserAccount(
    // Define properties for UserAccount if available in your JSON
)*/