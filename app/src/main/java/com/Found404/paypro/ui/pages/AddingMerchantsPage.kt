package com.Found404.paypro.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.Found404.paypro.ui.components.PayProTitle
import com.found404.network.service.MerchantService
import androidx.compose.ui.platform.LocalContext
import com.Found404.paypro.ui.components.MerchantItem
import com.Found404.paypro.ui.components.PayProNavigationDrawer
import com.found404.core.models.MerchantResponse
import kotlinx.coroutines.launch

enum class LoadingState {
    Loading, Loaded, Error
}

@Composable
fun AddingMerchants(navController: NavController) {
    val merchantService = MerchantService()
    val context = LocalContext.current
    var merchants by remember { mutableStateOf<List<MerchantResponse>>(emptyList()) }
    var loadingState by remember { mutableStateOf(LoadingState.Loading) }
    val coroutineScope = rememberCoroutineScope()

    fun updateMerchantsList() {
        loadingState = LoadingState.Loading
        coroutineScope.launch {
            merchantService.getMerchantsForUser(context) { retrievedMerchants, errorMessage ->
                if (retrievedMerchants != null) {
                    merchants = retrievedMerchants
                    loadingState = LoadingState.Loaded
                } else {
                    println("Error fetching merchants: $errorMessage")
                    merchants = emptyList()
                    loadingState = LoadingState.Error
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        updateMerchantsList()
    }

    println("merchants " + merchants)
    println("2")

    Box(modifier = Modifier.fillMaxSize()) {
        when (loadingState) {
            LoadingState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LoadingState.Loaded -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(bottom = 76.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    item { PayProTitle(text = "PayPro") }
                    println("aaaaa")
                    items(merchants) { merchant ->
                        MerchantItem(merchant,
                            onDeleteMerchant = { merchantId ->
                                coroutineScope.launch {
                                    val response = merchantService.deleteMerchant(merchantId, context)
                                    if (response?.success == true) {
                                        updateMerchantsList()
                                    } else {
                                        println("Error deleting merchant: ${response?.errorMessage}")
                                    }
                                }
                            },
                            onDeleteTerminal = { terminalId ->
                                coroutineScope.launch {
                                    val response = merchantService.deleteTerminal(merchant.id, terminalId, context)
                                    if (response?.success == true) {
                                        updateMerchantsList()
                                    } else {
                                        println("Error deleting terminal: ${response?.errorMessage}")
                                    }
                                }
                            }
                        )
                    }
                }
            }
            LoadingState.Error -> {
                Text("Error loading merchants", modifier = Modifier.align(Alignment.Center))
            }
        }

        //PayProNavigationDrawer(navController)

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { navController.navigate("merchantName") },
            shape = CircleShape,
            containerColor = Color.Blue,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}
