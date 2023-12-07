package com.Found404.paypro.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MerchantName(
    onButtonNextClick: () -> Unit,
    onButtonPrevClick: () -> Unit
) {
    var merchantName by remember { mutableStateOf("Eg. Konzum") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            text = "What's your merchant name?",
            fontSize = 50.sp,
            lineHeight = 50.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(
                vertical = 100.dp,
                horizontal = 20.dp
                )
        )
        TextField(
            value = merchantName,
            onValueChange = { merchantName = it },
        )
        Box(modifier = Modifier.fillMaxSize()){
            Button(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp)
                    .size(
                        width = 130.dp,
                        height = 60.dp),
                onClick = {
                    onButtonNextClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text(text = "Next",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp)
                    .size(
                        width = 130.dp,
                        height = 60.dp),
                onClick = {
                    onButtonPrevClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text(text = "Previous",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun MerchantName() {
    MerchantName(onButtonNextClick = {}, onButtonPrevClick = {})
}
