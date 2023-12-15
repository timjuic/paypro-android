package com.found404.ValidationLogic

class MerchantDataValidator {

    fun validateMerchantName(merchantName: String) : Boolean {
        val nameRegex = "^[A-Z][a-zA-Z0-9\\s&'m@_\$-]{1,49}\$"

        return merchantName.matches(nameRegex.toRegex())
    }

    fun validateStreetName(merchantStreetName: String) : Boolean {
        val streetNameRegex = "^[a-zA-Z]+$"

        return merchantStreetName.matches(streetNameRegex.toRegex())
    }

    fun validateCityName(merchantCityName : String) : Boolean {
        val cityNameRegex = "^[a-zA-Z]+$"

        return merchantCityName.matches(cityNameRegex.toRegex())
    }

    fun validatePostCode(merchantPostCode : Int) : Boolean {
        val postCodeRegex = "^[1-9]+$"

        return merchantPostCode.toString().matches(postCodeRegex.toRegex())
    }

    fun validateStreetNumber(merchantStreetNumber: Int) : Boolean {
        val streetNumberRegex = "^[1-9]+$"

        return merchantStreetNumber.toString().matches(streetNumberRegex.toRegex())
    }

}