package com.Found404.paypro.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.TextInput


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletingMerchants(
){

    var merchantName by remember { mutableStateOf("Eg. Konzum") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "PayPro",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .wrapContentSize(Alignment.TopCenter)
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.LightGray)
        ){
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Enter your full Merchant name to delete",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                TextInput(value = merchantName, onValueChange = {newValue -> merchantName = newValue})
                PayProButton(text = "Delete", onClick = { /*TODO*/ }, buttonColor = Color.Red)
                }
            }
        }
    }
@Preview
@Composable
fun DeletingMerchantPreview() {
    DeletingMerchants()
}
