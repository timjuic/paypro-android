package com.found404.ValidationLogic

class MerchantDataValidator {

    fun validateMerchantName(merchantName: String) : ValidationStatus {
        val nameRegex = "^[A-Z][a-zA-Z0-9\\s&'m@_\$-]{1,49}\$"

        return if(merchantName.matches(nameRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Invalid merchant name")
        }
    }

    fun validateStreetName(merchantStreetName: String) : ValidationStatus {
        val streetNameRegex = "^[a-zA-Z]+$"

        return if (merchantStreetName.matches(streetNameRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Invalid street name")
        }
    }

    fun validateCityName(merchantCityName : String) : ValidationStatus {
        val cityNameRegex = "^[a-zA-Z]+$"

        return if (merchantCityName.matches(cityNameRegex.toRegex())){
            ValidationStatus(true)
        } else {
            return ValidationStatus(false, "Invalid city name")
        }
    }

    fun validatePostCode(merchantPostCode : Int) : ValidationStatus {
        val postCodeRegex = "^[1-9]+$"

        return if (merchantPostCode.toString().matches(postCodeRegex.toRegex())) {
            ValidationStatus(true)
        } else {
            return ValidationStatus(false, "Invalid postal code")
        }
    }

    fun validateStreetNumber(merchantStreetNumber: Int) : ValidationStatus {
        val streetNumberRegex = "^[1-9]+$"

        return if (merchantStreetNumber.toString().matches(streetNumberRegex.toRegex())){
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Invalid street number")
        }
    }

    fun validateAll (
        merchantName: String,
        merchantStreetName: String,
        merchantCityName: String,
        merchantPostCode: Int,
        merchantStreetNumber: Int
    ) : ValidationStatus {
        val merchantNameStatus = validateMerchantName(merchantName)
        val merchantStreetNameStatus = validateStreetName(merchantStreetName)
        val merchantCityNameStatus = validateCityName(merchantCityName)
        val merchantPostCodeStatus = validatePostCode(merchantPostCode)
        val merchantStreetNumberStatus = validateStreetNumber(merchantStreetNumber)

        val allStatus = listOf(
            merchantNameStatus, merchantStreetNameStatus, merchantCityNameStatus, merchantPostCodeStatus, merchantStreetNumberStatus
        )

        val validationFailed = allStatus.find { !it.success }
        return validationFailed ?: ValidationStatus(true)
    }
}

data class ValidationStatus(val success: Boolean, val errorMessage: String? = null)