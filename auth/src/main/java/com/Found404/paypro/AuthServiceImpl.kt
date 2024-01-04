package com.Found404.paypro


import android.content.Context
import com.Found404.paypro.responses.RegistrationResponse
import com.found404.core.auth.AuthConfig
import com.found404.core.auth.LoginData

class AuthServiceImpl(
    authConfig: AuthConfig
) : AuthFacade {
    private lateinit var jwtAuthStrategy: JWTAuthStrategy
    private lateinit var userDataService: UserDataServiceImpl

    init {
        initializeServices()
        AuthConfigManager.initialize(authConfig)
    }

    private fun initializeServices() {
        userDataService = UserDataServiceImpl()
        jwtAuthStrategy = JWTAuthStrategyImpl(userDataService)
    }


    override suspend fun isJwtValid(context: Context): Boolean {
        return jwtAuthStrategy.isJwtValid(context)
    }

    override suspend fun refreshAccessToken(endpointPath: String, refreshToken: String): String? {
        return jwtAuthStrategy.refreshAccessToken(endpointPath, refreshToken)
    }

    override suspend fun registerUser(
        endpointPath: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): RegistrationResponse {
        return jwtAuthStrategy.registerUser(endpointPath, firstName, lastName, email, password)
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