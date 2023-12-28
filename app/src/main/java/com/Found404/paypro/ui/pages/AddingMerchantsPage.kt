package com.Found404.paypro.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.ui.components.PayProTitle
import com.found404.network.service.MerchantService
import androidx.compose.ui.platform.LocalContext
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
            try {
                val retrievedMerchants = merchantService.getMerchantsForUser(context)
                print("retrievedMerchants " + retrievedMerchants)
                if (retrievedMerchants != null && retrievedMerchants.isNotEmpty()) {
                    merchants = retrievedMerchants
                } else {
                    println("Dohvaćanje trgovaca nije uspjelo.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("Greška prilikom dohvaćanja trgovaca.")
            }
        }
    }
    println("merchants = " + merchants)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                MerchantItem(merchant)
            }
        }

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

@Composable
fun MerchantItem(merchant: MerchantResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Your Merchant",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(text = merchant.merchantName, color = Color.Black, style = TextStyle(fontSize = 30.sp))
            Text(text = "${merchant.address.streetName}, ${merchant.address.city}")
            Text(text = "Street No: ${merchant.address.streetNumber}, Postal Code: ${merchant.address.postalCode}")
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = { /* Handle Edit Click */ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Merchant",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
