package com.Found404.paypro

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Found404.paypro.ui.pages.AddingMerchants
import com.Found404.paypro.ui.pages.CardPayments
import com.Found404.paypro.ui.pages.MerchantAddress
import com.Found404.paypro.ui.pages.MerchantCreated
import com.Found404.paypro.ui.pages.MerchantName
import com.Found404.paypro.ui.pages.RegisterScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "merchantName"){
                        composable("registration"){
                            RegisterScreen(navController = navController)
                        }
                        composable("addingMerchants"){
                            AddingMerchants(navController = navController)
                        }
                        composable("merchantName") {
                            MerchantName(navController = navController)
                        }
                        composable("merchantAddress"){
                            MerchantAddress(navController = navController)
                        }
                        composable("cardPayments"){
                            CardPayments(navController = navController)
                        }
                        composable("merchantCreated"){
                            MerchantCreated(navController = navController)
                        }
                    }
}