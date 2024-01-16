import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
    import androidx.compose.material3.OutlinedTextField
    import androidx.compose.material3.RadioButton
    import androidx.compose.material3.Text
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.input.KeyboardType
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.compose.ui.window.Dialog
    import com.Found404.paypro.ui.pages.CreateRow
    import com.found404.core.models.CreditCardType
    import com.found404.core.models.EditMerchant
    import com.found404.core.models.Merchant
    import com.found404.core.models.MerchantResponse
    import com.found404.core.models.StatusType
    import com.found404.network.service.CreditCardsService
    import kotlinx.coroutines.launch

    @Composable
    fun EditMerchantPopup(
        editMerchant: EditMerchant,
        onConfirm: (EditMerchant) -> Unit,
        onCancel: () -> Unit
    ) {
        var merchantName by remember { mutableStateOf(editMerchant.merchantName) }
        var city by remember { mutableStateOf(editMerchant.address.city) }
        var streetName by remember { mutableStateOf(editMerchant.address.streetName) }
        var postalCode by remember { mutableStateOf(editMerchant.address.postalCode.toString()) }
        var streetNumber by remember { mutableStateOf(editMerchant.address.streetNumber.toString()) }
        var cardTypes: List<CreditCardType> by remember { mutableStateOf(emptyList()) }
        var selectedCardTypes: List<CreditCardType> by remember { mutableStateOf(editMerchant.acceptedCards) }
        var selectedStatus by remember { mutableStateOf(editMerchant.status) }
        val scrollState = rememberScrollState()

        val coroutineScope = rememberCoroutineScope()
        val creditCardsService = CreditCardsService()
        val context = LocalContext.current
        var merchantModel by remember { mutableStateOf(Merchant()) }

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

        Dialog(
           onDismissRequest = onCancel
        ){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(width = 300.dp, height = 600.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .verticalScroll(scrollState)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Edit Merchant", style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp))

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = merchantName,
                        onValueChange = { merchantName = it },
                        label = { Text("Merchant Name") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("City") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = streetName,
                        onValueChange = { streetName = it },
                        label = { Text("Street Name") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = postalCode,
                        onValueChange = { postalCode = it },
                        label = { Text("Postal Code") }
                    )

                    Column(modifier = Modifier.fillMaxWidth()) {
                        cardTypes.forEach { cardType ->
                            var isChecked by remember { mutableStateOf(cardType.cardBrandId in selectedCardTypes.map { it.cardBrandId }) }
                            CreateRow(
                                cardName = cardType.name,
                                cardId = cardType.cardBrandId,
                                isChecked = isChecked
                            ) { checked ->
                                isChecked = checked
                                if (isChecked) {
                                    selectedCardTypes = selectedCardTypes + cardType
                                } else {
                                    selectedCardTypes = selectedCardTypes.filter { it.cardBrandId != cardType.cardBrandId }
                                }
                            }
                        }
                    }


                    Text("Status:", style = TextStyle(fontSize = 18.sp))
                    Row {
                        RadioButton(
                            selected = selectedStatus == 1,
                            onClick = { selectedStatus = 1 }
                        )
                        Text("Active")

                        Spacer(modifier = Modifier.width(16.dp))

                        RadioButton(
                            selected = selectedStatus == 2,
                            onClick = { selectedStatus = 2 }
                        )
                        Text("Disabled")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        Button(onClick = {
                            val updatedMerchant = editMerchant.copy(
                                merchantName = merchantName,
                                address = editMerchant.address.copy(
                                    city = city,
                                    streetName = streetName,
                                    postalCode = postalCode.toInt(),
                                    streetNumber = streetNumber,
                                ),
                                acceptedCards = selectedCardTypes,
                                status = selectedStatus
                            )
                            onConfirm(updatedMerchant)
                        }) {
                            Text("Save")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(onClick = onCancel) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }

    }
