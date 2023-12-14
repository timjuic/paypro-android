package com.found404.ValidationLogic

class MerchantDataValidator {

    fun validateMerchantName(merchantName: String) : ValidationStatus {
        val nameRegex = "^[A-Z][a-zA-Z0-9\\s&'m@_\$-]{1,49}\$"

        return if (merchantName.matches(nameRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Please input a valid merchant name!")
        }
    }

    fun validateStreetName(merchantStreetName: String) : ValidationStatus {
        val streetNameRegex = "^[a-zA-Z]+$"

        return if (merchantStreetName.matches(streetNameRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Please input a valid merchant name!")
        }
    }

    fun validateCityName(merchantCityName : String) : ValidationStatus {
        val cityNameRegex = "^[a-zA-Z]+$"

        return if (merchantCityName.matches(cityNameRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Please input a valid merchant name!")
        }
    }

    fun validatePostCode(merchantPostCode : Int) : ValidationStatus {
        val postCodeRegex = "^[1-9]+$"

        return if (merchantPostCode.toString().matches(postCodeRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Please input a valid merchant name!")
        }
    }

    fun validateStreetNumber(merchantStreetNumber: Int) : ValidationStatus {
        val streetNumberRegex = "^[1-9]+$"

        return if (merchantStreetNumber.toString().matches(streetNumberRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Please input a valid merchant name!")
        }
    }

}

data class ValidationStatus(val success: Boolean, val errorMessage: String? = null)