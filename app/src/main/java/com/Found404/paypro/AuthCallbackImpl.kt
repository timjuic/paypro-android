package com.Found404.paypro

import androidx.navigation.NavController
import com.found404.core.auth.AuthCallback

class AuthCallbackImpl(private val navController: NavController) : AuthCallback {
    override fun navigateTo(destination: String) {
        navController.navigate(destination)
    }
}