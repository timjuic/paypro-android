package com.Found404.paypro.ui.pages


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.MerchantService
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.TextInput
import com.Found404.paypro.ui.components.Title
import responses.Merchant

@Composable
fun DisplayListOfMerchant(viewModel: MerchantService){

    val dataState = remember { mutableStateOf<List<Merchant>>(emptyList())}
    val popupState = remember { mutableStateMapOf<String, Boolean>() }
    var merchantName by remember { mutableStateOf("") }

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
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)


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
                        onClick = {
                            merchantName = Merchant.merchantName
                            popupState[Merchant.merchantName] = true
                        }
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
    }
    merchants.forEach { Merchant ->
        if (popupState[Merchant.merchantName] == true) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PopupContent(
                        merchantName = merchantName,
                        onClosePopup = { popupState[Merchant.merchantName] = false }
                    )
                }
        }
    }
}

@Composable
fun PopupContent(merchantName: String, onClosePopup: () -> Unit) {
    var currentMerchantName by remember { mutableStateOf(merchantName) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Enter your full Merchant name to delete",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                TextInput(
                    value = currentMerchantName,
                    onValueChange = { newValue -> currentMerchantName = newValue }
                )
                PayProButton(
                    text = "Delete", onClick = {
                        onClosePopup()
                    },
                    buttonColor = Color.Red
                )
            }
        }
    }

@Preview
@Composable
fun DisplayingListOfMerchantPreview() {
    DisplayListOfMerchant(viewModel = MerchantService())
}

