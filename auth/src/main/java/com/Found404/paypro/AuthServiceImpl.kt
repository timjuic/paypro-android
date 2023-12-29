package com.Found404.paypro


import android.content.Context
import com.Found404.paypro.responses.LoginData
import com.Found404.paypro.responses.LoginResponse
import com.Found404.paypro.responses.RegistrationResponse

class AuthServiceImpl(
    authConfig: AuthConfig
) : AuthFacade {
    init {
        AuthConfigManager.initialize(authConfig)
    }

    private lateinit var authenticationService: AuthenticationService
    private lateinit var registrationService: RegistrationService
    private lateinit var userDataService: UserDataService

    init {
        initializeServices()
        AuthConfigManager.initialize(authConfig)
    }

    private fun initializeServices() {
        authenticationService = AuthenticationServiceImpl(userDataService)
        registrationService = RegistrationServiceImpl()
        userDataService = UserDataServiceImpl()
    }

    override suspend fun loginUser(
        endpointPath: String,
        email: String,
        password: String,
        context: Context
    ): LoginResponse {
        return authenticationService.loginUser(endpointPath, email, password, context)
    }

    override suspend fun isJwtValid(context: Context): Boolean {
        return authenticationService.isJwtValid(context)
    }

    override suspend fun refreshAccessToken(endpointPath: String, refreshToken: String): String? {
        return authenticationService.refreshAccessToken(endpointPath, refreshToken)
    }

    override suspend fun registerUser(
        endpointPath: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): RegistrationResponse {
        return registrationService.registerUser(endpointPath, firstName, lastName, email, password)
    }

    override fun saveLoggedInUser(loginData: LoginData?, context: Context) {
        return userDataService.saveLoggedInUser(loginData, context)
    }

    override fun getLoggedInUser(context: Context): UserData {
        return userDataService.getLoggedInUser(context)
    }

    override fun logoutUser(context: Context) {
        return userDataService.logoutUser(context)
    }

    override fun getAuthToken(context: Context): String? {
        return userDataService.getAuthToken(context)
    }

    override fun saveAccessToken(accessToken: String, context: Context) {
        return userDataService.saveAccessToken(accessToken, context)
    }
}