package com.Found404.paypro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.Found404.paypro.ui.pages.RegisterScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Found404.paypro.ui.pages.AddingMerchants
import com.Found404.paypro.ui.pages.CardPayments
import com.Found404.paypro.ui.pages.MerchantAddress
import com.Found404.paypro.ui.pages.MerchantCreated
import com.Found404.paypro.ui.pages.MerchantName
import com.Found404.paypro.ui.theme.PayProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PayProTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "addingMerchants"){
                        composable("addingMerchants"){
//                            EntryPage(
//                                TODO implement entry page for login and registration
//                            )
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
                            MerchantName(
                                onButtonNextClick = {
                                        navController.navigate("merchantAddress")
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    PayProTheme {
    }
}