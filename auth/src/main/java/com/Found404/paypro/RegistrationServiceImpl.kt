package com.Found404.paypro

class RegistrationServiceImpl : RegistrationService {
    val validator: RegistrationValidator = RegistrationValidator()

    override suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): RegistrationResult {
        TODO("Not yet implemented")
    }


}