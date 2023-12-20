package com.Found404.paypro.ui.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.found404.core.models.CardBrandType
import com.found404.core.models.CreditCardType
import com.found404.core.models.Merchant
import com.found404.core.models.MerchantViewModel
import com.found404.core.models.SharedPreferencesManager
import com.found404.network.result.AddingMerchantsResult
import com.found404.network.service.implementation.AddingMerchantsServiceImplementation
import com.found404.network.service.CreditCardsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CardPayments(
    navController: NavController
) {
    var merchantModel by remember { mutableStateOf(Merchant()) }
    var atLeastOneChecked by remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val sharedPreferencesManager = getAllSavedData(context)
    val defaultStatus = "Active"

    val addingMerchantsService = AddingMerchantsServiceImplementation()
    val creditCardsService = CreditCardsService()

    var addingMerchantsResult by remember {
        mutableStateOf<AddingMerchantsResult?>(null)
    }

    var cardTypes: List<CreditCardType> by remember { mutableStateOf(emptyList()) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            try {
                val retrievedCardTypes = creditCardsService.getCreditCardTypes(context)
                print("retrievedCardTypes " + retrievedCardTypes)
                if (retrievedCardTypes != null && retrievedCardTypes.isNotEmpty()) {
                    cardTypes = retrievedCardTypes
                } else {
                    println("Dohvaćanje tipova kartica nije uspjelo.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("Greška prilikom dohvaćanja tipova kartica.")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
        println("Card Types: $cardTypes")

        Column(modifier = Modifier.fillMaxWidth()) {
            cardTypes.forEach { cardType ->
                var isChecked by remember { mutableStateOf(false) }
                CreateRow(
                    cardName = cardType.name,
                    cardId = cardType.cardBrandId,
                    isChecked = isChecked
                ) { checked ->
                    isChecked = checked
                    merchantModel = if (isChecked) {
                        merchantModel.copy(cardTypes = merchantModel.cardTypes + CardBrandType.values().first { it.cardBrandId == cardType.cardBrandId })
                    } else {
                        merchantModel.copy(cardTypes = merchantModel.cardTypes - CardBrandType.values().first { it.cardBrandId == cardType.cardBrandId })
                    }
                    atLeastOneChecked = merchantModel.cardTypes.isNotEmpty()
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    navController.navigate("merchantAddress")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text(
                    text = "Previous",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val selectedCards = cardTypes.filter { cardType ->
                            merchantModel.cardTypes.any { it.cardBrandId == cardType.cardBrandId }
                        }.map { cardType ->
                            mapOf("cardBrandId" to cardType.cardBrandId, "name" to cardType.name)
                        }

                        if (selectedCards.isNotEmpty()) {
                            addingMerchantsResult = addingMerchantsService.addMerchant(
                                context,
                                sharedPreferencesManager.merchantData.fullName,
                                sharedPreferencesManager.merchantData.streetName,
                                sharedPreferencesManager.merchantData.cityName,
                                sharedPreferencesManager.merchantData.postCode,
                                sharedPreferencesManager.merchantData.streetNumber,
                                selectedCards,
                                defaultStatus
                            )
                            println(
                                "adding merchants result " + addingMerchantsResult!!.success + " " + addingMerchantsResult!!.errorMessage + " " + addingMerchantsResult!!.message + " "

                            )
                        } else {
                            withContext(Dispatchers.Main) {
                                showErrorMessage = true
                                Toast.makeText(
                                    context,
                                    "Please select at least one option!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        if (atLeastOneChecked) {
                            navController.navigate("merchantCreated")
                            showErrorMessage = false
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text(
                    text = "Finish",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        if (showErrorMessage) {
            Text(
                text = "Please select at least one option!",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun CreateRow(cardName: String, cardId: Int, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
        Text(
            text = cardName,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

fun getAllSavedData(context: Context): MerchantViewModel {
    val merchantViewModel = MerchantViewModel()

    merchantViewModel.merchantData.fullName = SharedPreferencesManager.getMerchantName(context).toString()
    merchantViewModel.merchantData.streetName = SharedPreferencesManager.getMerchantStreetName(context).toString()
    merchantViewModel.merchantData.cityName = SharedPreferencesManager.getMerchantCity(context).toString()
    merchantViewModel.merchantData.postCode = SharedPreferencesManager.getMerchantPostCode(context)
    merchantViewModel.merchantData.streetNumber = SharedPreferencesManager.getMerchantStreetNumber(context)

    return merchantViewModel
}
