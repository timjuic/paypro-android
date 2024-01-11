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

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "welcome"){
        val authServiceImpl = AuthDependencyProvider.getInstance().getAuthService()

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
                WelcomePage(navController = navController)
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
        composable("addingMerchants?mid={mid}") { backStackEntry ->
            val mid = backStackEntry.arguments?.getString("mid")?.toIntOrNull() ?: 0

            AddingTerminal(navController = navController, mid = mid)
        }



    }
}