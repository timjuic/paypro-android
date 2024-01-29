package com.Found404.paypro.ui.pages

import MerchantItem
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
import com.Found404.paypro.ui.components.PayProNavigationDrawer
import com.found404.core.models.MerchantResponse
import com.found404.core.models.SharedPreferencesManager
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

    PayProNavigationDrawer(navController = navController) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (loadingState) {
                LoadingState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                LoadingState.Loaded -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        item { PayProTitle(text = "Your Merchants", modifier = Modifier.padding(top = 70.dp)) }

                        items(merchants) { merchant ->
                            MerchantItem(navController, merchant,
                                onEditMerchant = { updatedMerchant ->
                                    coroutineScope.launch {
                                        val response = merchantService.editMerchant(
                                            context,
                                            updatedMerchant.id,
                                            updatedMerchant.merchantName,
                                            updatedMerchant.address.streetName,
                                            updatedMerchant.address.city,
                                            updatedMerchant.address.postalCode,
                                            updatedMerchant.address.streetNumber,
                                            updatedMerchant.acceptedCards.map { cardType ->
                                                mapOf(
                                                    "cardBrandId" to cardType.cardBrandId,
                                                    "name" to cardType.name
                                                )
                                            },
                                            updatedMerchant.status
                                        )
                                        if (response?.success == true) {
                                            navController.navigate("AddingMerchants")
                                        } else {
                                            println("Error updating merchant: ${response?.errorMessage}")
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

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {
                    SharedPreferencesManager.clearAll(context)
                    navController.navigate("merchantName") },
                shape = CircleShape,
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}