package com.Found404.paypro.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.Found404.paypro.ui.components.PayProNavigationDrawer
import com.found404.core.models.Terminal
import com.found404.network.service.MerchantService
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.found404.network.service.Merchant


@Composable
fun AddingMerchants(
    navController: NavController,
    merchantService: MerchantService
) {
    val context = LocalContext.current
    var merchantsWithTerminals by remember { mutableStateOf(emptyList<Pair<Merchant, List<Terminal>>>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            val merchants = merchantService.getMerchantsForUser(context)
            println("Dohvaćeni trgovci: $merchants")

            val terminalsByMerchant = merchants?.associateWith { merchant ->
                merchantService.getTerminalsForMerchant(merchant.id, context) ?: emptyList()
            } ?: emptyMap()

            println("Trgovci s terminalima: $terminalsByMerchant")
            merchantsWithTerminals = terminalsByMerchant.toList()
        }
    }


    PayProNavigationDrawer(navController)

    Box(modifier = Modifier.fillMaxSize()) {
        merchantsWithTerminals.forEach { (merchant, terminals) ->
            MerchantCard(merchant, terminals)
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
                .align(Alignment.BottomEnd)
                .zIndex(0f),
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
fun MerchantCard(merchant: Merchant, terminals: List<Terminal>) {
    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = merchant.fullName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            terminals.forEach { terminal ->
                Text(text = "Terminal: ${terminal.terminalKey}", fontSize = 14.sp)
            }
        }
    }
}
