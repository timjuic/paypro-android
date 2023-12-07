package com.Found404.paypro

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Found404.paypro.ui.theme.PayProTheme
import androidx.navigation.compose.rememberNavController
import com.Found404.paypro.ui.pages.AddingMerchants
import com.Found404.paypro.ui.pages.CardPayments
import com.Found404.paypro.ui.pages.MerchantAddress
import com.Found404.paypro.ui.pages.MerchantName
import com.Found404.paypro.ui.pages.MerchantCreated
import kotlin.math.log

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
                        composable("entry"){
//                            EntryPage(
//                                TODO implement entry page for login and registration
//                            )
                        }
                        composable("addingMerchants"){
                            AddingMerchants(
                                onCreateMerchantButtonClick = {
                                    navController.navigate("merchantName")
                                }
                            )
                        }
                        composable("merchantName"){
                            MerchantName(
                                onButtonNextClick = {
                                    navController.navigate("merchantAddress")
                                }
                            )
                        }
                        composable("merchantAddress"){
                            MerchantAddress(
                                onButtonNextClick = {
                                    navController.navigate("cardPayments")
                                }
                            )
                        }
                        composable("cardPayments"){
                            CardPayments(
                                onButtonFinishClick = {
                                    navController.navigate("merchantCreated")
                                }
                            )
                        }
                        composable("merchantCreated"){
                            MerchantCreated(
                                onButtonFinishClick = {
                                    navController.navigate("home")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PayProTheme {
        Greeting("Android")
    }
}