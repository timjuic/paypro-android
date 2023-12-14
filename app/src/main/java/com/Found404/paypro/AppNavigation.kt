package com.Found404.paypro

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Found404.paypro.context.MerchantAuth
import com.Found404.paypro.ui.pages.AddingMerchants
import com.Found404.paypro.ui.pages.CardPayments
import com.Found404.paypro.ui.pages.MerchantAddress
import com.Found404.paypro.ui.pages.MerchantCreated
import com.Found404.paypro.ui.pages.MerchantName
import com.Found404.paypro.viewModels.AddMerchantViewModel
import com.Found404.paypro.ui.pages.RegisterScreen

@Composable
fun AppNavigation(viewModelMerchant: AddMerchantViewModel) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "registration"){
                        composable("registration"){
                            RegisterScreen(navController = navController)
                        }
                        composable("addingMerchants"){
                            AddingMerchants(
                                onCreateMerchantButtonClick = {
                                    navController.navigate("merchantName")
                                },
                                onButtonCancelClick = {
                                    navController.navigate("merchantCreated") //TODO change to home page once it is completed
                                }
                            )
                        }
                        composable("merchantName") {
                            val context = LocalContext.current
                            MerchantName(
                                viewModel = viewModelMerchant,
                                onButtonNextClick = {
                                    val merchantData = MerchantAuth.merchantData!!
                                    if (merchantData.fullName != null) {
                                        navController.navigate("merchantAddress")
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Please input a merchant name",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                onButtonPrevClick = {
                                    navController.navigate("addingMerchants")
                                }
                            )
                        }
                        composable("merchantAddress"){
                            MerchantAddress(
                                onButtonNextClick = {
                                    navController.navigate("cardPayments")
                                },
                                onButtonPrevClick = {
                                    navController.navigate("merchantName")
                                }
                            )
                        }
                        composable("cardPayments"){
                            CardPayments(
                                onButtonFinishClick = {
                                    navController.navigate("merchantCreated")
                                },
                                onButtonPrevClick = {
                                    navController.navigate("merchantAddress")
                                }
                            )
                        }
                        composable("merchantCreated"){
                            MerchantCreated(
                                onButtonFinishClick = {
                                    navController.navigate("addingMerchants") //TODO change to home page once it is completed
                                },
                                onButtonPrevClick = {
                                    navController.navigate("cardPayments")
                                }
                            )
                        }
                    }
}