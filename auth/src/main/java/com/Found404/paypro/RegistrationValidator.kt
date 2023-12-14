package com.Found404.paypro

class RegistrationValidator() {

    fun validateEmail(email: String): ValidationStatus {
        return if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Invalid email address")
        }
    }

    fun validateCommonUsername(username: String): ValidationStatus {
        return if (username.length >= 6) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Username must be at least 6 characters long")
        }
    }

    fun validateWeakPassword(password: String): ValidationStatus {
        return if (password.length >= 8) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Weak password: It must be at least 8 characters long.")
        }
    }

    fun validateMediumPassword(password: String): ValidationStatus {
        return when {
            password.length < 8 -> ValidationStatus(false, "Medium password: It must be at least 8 characters long.")
            !password.any { it.isDigit() } -> ValidationStatus(false, "Medium password: It must contain at least one digit.")
            else -> ValidationStatus(true)
        }
    }

    fun validateStrongPassword(password: String): ValidationStatus {
        return when {
            password.length < 8 -> ValidationStatus(false, "Strong password: It must be at least 8 characters long.")
            !password.any { it.isDigit() } -> ValidationStatus(false, "Strong password: It must contain at least one digit.")
            !password.any { it.isUpperCase() } -> ValidationStatus(false, "Strong password: It must contain at least one uppercase letter.")
            !password.any { it.isLowerCase() } -> ValidationStatus(false, "Strong password: It must contain at least one lowercase letter.")
            else -> ValidationStatus(true)
        }
    }

    fun validateFirstName(firstName: String): ValidationStatus {
        return if (firstName.isNotEmpty()) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "First name cannot be empty")
        }
    }

    fun validateLastName(lastName: String): ValidationStatus {
        return if (lastName.isNotEmpty()) {
            ValidationStatus(true)
        } else {
            ValidationStatus(false, "Last name cannot be empty")
        }
    }
}

data class ValidationStatus(val success: Boolean, val errorMessage: String? = null)
