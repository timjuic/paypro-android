package com.Found404.paypro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.Found404.paypro.ui.theme.PayProTheme
import com.Found404.paypro.viewModels.AddMerchantViewModel

class MainActivity : ComponentActivity() {
    private val viewModelMerchant: AddMerchantViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation(viewModelMerchant = viewModelMerchant)

//            PayProTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val navController = rememberNavController()
//                    val merchantViewModel: AddMerchantViewModel = viewModel()
//
//
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    PayProTheme {
    }
}