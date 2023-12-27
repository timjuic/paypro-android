package com.Found404.paypro

class AuthValidator() {
    companion object {
        fun validateEmail(email: String): ValidationStatus {
            val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"

            return if (email.matches(emailRegex.toRegex())) {
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
            val nameRegex = "^[\\p{L}]+$"

            return if (firstName.matches(nameRegex.toRegex())) {
                ValidationStatus(true)
            } else {
                ValidationStatus(false, "First name should only contain letters")
            }
        }

        fun validateLastName(lastName: String): ValidationStatus {
            val nameRegex = "^[\\p{L}]+$"

            return if (lastName.matches(nameRegex.toRegex())) {
                ValidationStatus(true)
            } else {
                ValidationStatus(false, "Last name should only contain letters")
            }
        }

        fun validateAll(
            firstName: String,
            lastName: String,
            email: String,
            password: String
        ): ValidationStatus {
            val emailStatus = validateEmail(email)
            val usernameStatus = validateCommonUsername(email)
            val weakPasswordStatus = validateWeakPassword(password)
            val firstNameStatus = validateFirstName(firstName)
            val lastNameStatus = validateLastName(lastName)

            val allStatus = listOf(
                emailStatus, usernameStatus, weakPasswordStatus, firstNameStatus, lastNameStatus
            )

            val validationFailed = allStatus.find { !it.success }
            return validationFailed ?: ValidationStatus(true)
        }
    }
}

data class ValidationStatus(val success: Boolean, val errorMessage: String? = null)
