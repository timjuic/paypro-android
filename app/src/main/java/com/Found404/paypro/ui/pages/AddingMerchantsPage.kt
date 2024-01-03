package com.Found404.paypro.ui.pages


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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

@Composable
fun AddingMerchants(navController: NavController) {
    val merchantService = MerchantService()
    val context = LocalContext.current
    var merchants by remember { mutableStateOf<List<MerchantResponse>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            val retrievedMerchants = merchantService.getMerchantsForUser(context)
            if (!retrievedMerchants.isNullOrEmpty()) {
                merchants = retrievedMerchants
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 76.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                PayProTitle(text = "PayPro")
            }
            items(merchants) { merchant ->
                MerchantItem(merchant, onDeleteTerminal = { terminalId ->
                    coroutineScope.launch {
                        val response = merchantService.deleteTerminal(merchant.merchantId, terminalId, context)
                        if (response?.success == true) {
                            val updatedMerchants = merchantService.getMerchantsForUser(context)
                            merchants = updatedMerchants ?: emptyList()
                        } else {
                            println("Error deleting terminal: ${response?.errorMessage}")
                        }
                    }
                })
            }
        }

        PayProNavigationDrawer(navController)

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                navController.navigate("merchantName")
            },
            shape = CircleShape,
            containerColor = Color.Blue,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}