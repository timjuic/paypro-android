package com.Found404.paypro

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Found404.paypro.ui.pages.AddingMerchants
import com.Found404.paypro.ui.pages.AddingTerminal
import com.Found404.paypro.ui.pages.CardPayments
import com.Found404.paypro.ui.pages.LoginPage
import com.Found404.paypro.ui.pages.MerchantAddress
import com.Found404.paypro.ui.pages.MerchantCreated
import com.Found404.paypro.ui.pages.MerchantName
import com.Found404.paypro.ui.pages.RegisterPage
import com.Found404.paypro.ui.pages.WelcomePage
import com.found404.network.service.MerchantService

@Composable
fun AppNavigation(onGoogleSignIn: () -> Unit) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "welcome"){
        val authServiceImpl = createAuthService("http://158.220.113.254:8086")

        composable("welcome") {
            val context = LocalContext.current
            var isJwtValid by remember {
                mutableStateOf<Boolean?>(false)
            }

            LaunchedEffect(authServiceImpl) {
                isJwtValid = authServiceImpl.isJwtValid(context)
            }

            if (isJwtValid == true) {
                AddingMerchants(navController = navController)
            } else {
                WelcomePage(navController = navController, onGoogleSignIn = onGoogleSignIn)
            }
        }

        composable("login") {
            LoginPage(navController = navController)
        }
        composable("registration") {
            RegisterPage(navController = navController)
        }
        composable("addingMerchants") {
            AddingMerchants(navController = navController)
        }
        composable("merchantName") {
            MerchantName(navController = navController)
        }
        composable("merchantAddress") {
            MerchantAddress(navController = navController)
        }
        composable("cardPayments") {
            CardPayments(navController = navController)
        }
        composable("merchantCreated") {
            MerchantCreated(navController = navController)
        }
        composable("addingTerminals") {
            AddingTerminal(navController = navController)
        }
    }
}