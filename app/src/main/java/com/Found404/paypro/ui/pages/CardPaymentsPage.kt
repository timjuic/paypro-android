package com.Found404.paypro.ui.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.found404.ValidationLogic.MerchantDataValidator
import com.found404.core.models.Merchant
import com.found404.network.AddingMerchantsResult
import com.found404.network.AddingMerchantsServiceImplementation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CardPayments(
    onButtonFinishClick: () -> Unit,
    onButtonPrevClick: () -> Unit
) {
    var merchantModel by remember { mutableStateOf(Merchant()) }
    var atLeastOneChecked by remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf(false) }

    val validator = MerchantDataValidator()
    val context = LocalContext.current

    val addingMerchantsService = AddingMerchantsServiceImplementation()
    var addingMerchantsResult by remember {
        mutableStateOf<AddingMerchantsResult?>(null)
    }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Select card payments that you accept",
            fontSize = 50.sp,
            lineHeight = 50.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(
                vertical = 32.dp,
                horizontal = 16.dp
            )
        )

        val cardTypes = listOf("Visa", "Master", "Maestro", "Diners", "American Express")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            for (cardType in cardTypes) {
                CreateRow(cardNameParam = cardType, cardTypeParam = merchantModel.cardTypes.contains(cardType)) {
                    merchantModel = if (merchantModel.cardTypes.contains(cardType)) {
                        merchantModel.copy(cardTypes = merchantModel.cardTypes - cardType)
                    } else {
                        merchantModel.copy(cardTypes = merchantModel.cardTypes + cardType)
                    }
                    atLeastOneChecked = cardTypes.any { type -> merchantModel.cardTypes.contains(type) }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                onClick = {
                    onButtonPrevClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text(
                    text = "Previous",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                onClick = {
                    coroutineScope.launch {
                        addingMerchantsResult = addingMerchantsService.addMerchant(
                            merchantModel.fullName,
                            merchantModel.streetName,
                            merchantModel.cityName,
                            merchantModel.postCode,
                            merchantModel.streetNumber
                        )

                        withContext(Dispatchers.Main){
                            if (addingMerchantsResult == null) {
                                showErrorMessage = true
                                Toast.makeText(
                                    context,
                                    "Please select at least one option!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }
                    if (atLeastOneChecked) {
                        onButtonFinishClick()
                        showErrorMessage = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text(
                    text = "Finish",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (showErrorMessage) {
            Text(
                text = "Please select at least one option!",
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun CreateRow(cardNameParam: String, cardTypeParam: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Checkbox(
            checked = cardTypeParam,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
        Text(
            text = cardNameParam,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun CardPayments() {
    CardPayments(onButtonFinishClick = {}, onButtonPrevClick = {})
}