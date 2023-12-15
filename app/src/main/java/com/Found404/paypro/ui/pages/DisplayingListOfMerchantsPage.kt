package com.Found404.paypro.ui.pages


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.MerchantService
import com.Found404.paypro.ui.components.Title
import responses.Merchant

@Composable
fun DisplayListOfMerchant(viewModel: MerchantService){

    val dataState = remember { mutableStateOf<List<Merchant>>(emptyList())}

    LaunchedEffect(true){
        val newMerchants =viewModel.getMerchantForUser("2")
        newMerchants?.let { dataState.value=it
            println("New Merchants: $it")
        }
    }

    val merchants = dataState.value
    println("Merchants: $merchants")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
        )
        {
            item { 
                Title(text = "PayPro")
            }
        items(merchants){Merchant ->
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(color = Color.LightGray)
                .padding(horizontal = 16.dp),

            ){
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
                    Text(text = Merchant.merchantName, color = Color.Black, style = TextStyle(fontSize = 30.sp))
                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = { /* Handle edit button click */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit Merchant",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                }
                //Text(text = Merchant.merchantName, color = Color.Black, style = TextStyle(fontSize = 30.sp))
            }
        }
    }
}
@Preview
@Composable
fun DisplayingListOfMerchantPreview() {
    DisplayListOfMerchant(viewModel = MerchantService())
}

