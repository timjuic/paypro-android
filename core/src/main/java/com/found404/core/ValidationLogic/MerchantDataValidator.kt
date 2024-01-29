package com.found404.core.models.ValidationLogic

class MerchantDataValidator {

    fun validateMerchantName(merchantName: String) : ValidationStatus {
        val nameRegex = "^[A-Z][a-zA-Z0-9\\s&'m@_\$-]{1,49}\$"

        return if(merchantName.matches(nameRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Invalid merchant name!")
        }
    }

    fun validateStreetName(merchantStreetName: String): ValidationStatus {
        val streetNameRegex = "^[a-zA-Z]+(\\s[a-zA-Z]+)*$"

        return if (merchantStreetName.matches(streetNameRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Invalid street name!")
        }
    }

    fun validateCityName(merchantCityName: String): ValidationStatus {
        val cityNameRegex = "^[a-zA-Z]+(\\s[a-zA-Z]+)*$"

        return if (merchantCityName.matches(cityNameRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Invalid city name!")
        }
    }

    fun validatePostCode(merchantPostCode : Int) : ValidationStatus {
        val postCodeRegex = "^[0-9]+$"

        return if (merchantPostCode.toString().matches(postCodeRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            return ValidationStatus(false, "Invalid postal code!")
        }
    }

    fun validateStreetNumber(merchantStreetNumber: String): ValidationStatus {
        val streetNumberRegex = "^[a-zA-Z0-9]+$"

        return if (merchantStreetNumber.matches(streetNumberRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Invalid street number!")
        }
    }
}

data class ValidationStatus(val success: Boolean, val errorMessage: String? = null)