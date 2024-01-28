package com.Found404.paypro

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
    val welcomePath = stringResource(id = R.string.welcome_page)
    val loginPath = stringResource(R.string.login_page)
    val registerPath = stringResource(id = R.string.registration_page)
    val addingMerchantsPath = stringResource(R.string.adding_merchants_page)
    val merchantNamePath = stringResource(id = R.string.merchant_name_page)
    val merchantAddressPath = stringResource(R.string.merchant_address_page)
    val cardPaymentsPath = stringResource(id = R.string.card_payments_page)
    val merchantCreatedPath = stringResource(R.string.merchant_created_page)

    val navController = rememberNavController()

    NavHost(navController, startDestination = welcomePath){
        val authServiceImpl = AuthDependencyProvider.getInstance().getAuthService()

        composable(welcomePath) {
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

        composable(loginPath) {
            LoginPage(navController = navController)
        }
        composable(registerPath) {
            RegisterPage(navController = navController)
        }
        composable(addingMerchantsPath) {
            AddingMerchants(navController = navController)
        }
        composable(merchantNamePath) {
            MerchantName(navController = navController)
        }
        composable(merchantAddressPath) {
            MerchantAddress(navController = navController)
        }
        composable(cardPaymentsPath) {
            CardPayments(navController = navController)
        }
        composable(merchantCreatedPath) {
            MerchantCreated(navController = navController)
        }
        composable("addingMerchants?mid={mid}") { backStackEntry ->
            val mid = backStackEntry.arguments?.getString("mid")?.toIntOrNull() ?: 0

            AddingTerminal(navController = navController, mid = mid)
        }



    }
}