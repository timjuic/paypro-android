package com.found404.core.models
enum class CardBrandType(val cardBrandId: Int, val brandName: String) {
    DINERS(1, "Diners"),
    MASTERCARD(2, "MasterCard"),
    VISA(3, "VISA"),
    DISCOVER(4, "Discover"),
    MAESTRO(5, "Maestro");
}
data class CreditCardType(
    val cardBrandId: Int,
    val name: String
)

