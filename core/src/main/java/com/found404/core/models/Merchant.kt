package com.found404.core.models
enum class StatusType(val id: Int, val value: String) {
    ACTIVE(1, "Active"),
    DISABLED(2, "Disabled"),
    PENDING(3, "Pending");
}
data class Merchant (
    var fullName: String = "",
    var cityName: String = "",
    var streetName: String = "",
    var postCode: Int = 0,
    var streetNumber: Int = 0,
    var cardTypes: List<CreditCardType> = emptyList(),
    var status: StatusType = StatusType.ACTIVE
)

data class MerchantResponse(
    val id: Int,
    val merchantName: String,
    val address: Address,
    val acceptedCards: List<CreditCardType>,
    val terminals: List<Terminal>,
    val createdAt: String,
    val status: Status
)

data class EditMerchant(
    val id: Int,
    val merchantName: String,
    val address: Address
)

data class Address(
    val streetName: String,
    val city: String,
    val streetNumber: String,
    val postalCode: Int
)


