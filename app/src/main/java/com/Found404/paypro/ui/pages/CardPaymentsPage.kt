package com.Found404.paypro.ui.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.ui.components.PayProTitle
import com.found404.core.models.CreditCardType
import com.found404.core.models.Merchant
import com.found404.core.models.MerchantViewModel
import com.found404.core.models.SharedPreferencesManager
import com.found404.network.result.AddingMerchantsResult
import com.found404.network.service.CreditCardsService
import com.found404.network.service.MerchantService
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

    val merchantService = MerchantService()
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
                if (retrievedCardTypes != null && retrievedCardTypes.isNotEmpty()) {
                    cardTypes = retrievedCardTypes
                } else {
                    println("Unable to retrieve credit cards.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error while retrieving credit card types.")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            PayProHeadline(
                text = "Select card payments that you accept",
                textAlignment = TextAlign.Start,
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
                        if (isChecked) {
                            merchantModel.cardTypes = merchantModel.cardTypes + cardType
                        } else {
                            merchantModel.cardTypes = merchantModel.cardTypes - cardType
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
                PayProButton(
                    text = "Previous",
                    onClick = {
                        navController.navigate("merchantAddress")
                    },
                    buttonColor = Color.Gray,
                    modifier = Modifier.size(150.dp, 70.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                PayProButton(
                    text = "Finish",
                    onClick = {
                        coroutineScope.launch {
                            val selectedCards = merchantModel.cardTypes.map { cardType ->
                                mapOf("cardBrandId" to cardType.cardBrandId, "name" to cardType.name)
                            }

                            if (selectedCards.isNotEmpty()) {
                                addingMerchantsResult = merchantService.addMerchant(
                                    context,
                                    sharedPreferencesManager.merchantData.fullName,
                                    sharedPreferencesManager.merchantData.streetName,
                                    sharedPreferencesManager.merchantData.cityName,
                                    sharedPreferencesManager.merchantData.postCode,
                                    sharedPreferencesManager.merchantData.streetNumber,
                                    selectedCards,
                                    defaultStatus
                                )
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Please select at least one option!", Toast.LENGTH_SHORT).show()
                                }
                            }
                            withContext(Dispatchers.Main) {
                                when (addingMerchantsResult?.errorMessage) {
                                    "ERR_INVALID_MERCHANT_NAME" -> {
                                        Toast.makeText(context, "Merchant name is in invalid format or not provided", Toast.LENGTH_LONG).show()
                                    }
                                    "ERR_MERCHANT_ALREADY_EXISTS" -> {
                                        Toast.makeText(context, "Merchant with the same name already exists", Toast.LENGTH_LONG).show()
                                    }
                                    "ERR_ACCEPTED_CARDS_NOT_DEFINED" -> {
                                        Toast.makeText(context, "Not a single accepted card defined", Toast.LENGTH_LONG).show()
                                    }
                                    null, "" -> {
                                        if (atLeastOneChecked) {
                                            navController.navigate("merchantCreated")
                                            showErrorMessage = false
                                        }
                                    }
                                    else -> {
                                        Toast.makeText(context, "An unknown error occurred", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    },
                    buttonColor = Color.Blue,
                    modifier = Modifier.size(120.dp, 70.dp)
                )
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
}



@Composable
fun CreateRow(cardName: String, cardId: Int, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
        PayProTitle(
            text = cardName,
            fontSize = 16.sp,
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
    merchantViewModel.merchantData.streetNumber = SharedPreferencesManager.getMerchantStreetNumber(context).toString()

    return merchantViewModel
}
