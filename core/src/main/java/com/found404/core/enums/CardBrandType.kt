package com.found404.core.enums

enum class CardBrandType(val id: Int, val text: String) {
    DINERS(1, "Diners"),
    MASTERCARD(2, "MasterCard"),
    VISA(3, "VISA"),
    DISCOVER(4, "Discover"),
    MAESTRO(5, "Maestro");

    companion object {
        fun getTextById(id: Int): String {
            return values().first {it.id == id}.text
        }
    }
}

