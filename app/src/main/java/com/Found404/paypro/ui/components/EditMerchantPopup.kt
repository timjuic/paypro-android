import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.ui.pages.CreateRow
import com.found404.core.models.CreditCardType
import com.found404.core.models.EditMerchant
import com.found404.core.models.Merchant
import com.found404.core.models.MerchantResponse
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
    var selectedCardTypes by remember { mutableStateOf(editMerchant.acceptedCards) }

    val coroutineScope = rememberCoroutineScope()
    val creditCardsService = CreditCardsService()
    val context = LocalContext.current
    var merchantModel by remember { mutableStateOf(Merchant()) }

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

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
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
                    var isChecked by remember { mutableStateOf(cardType in selectedCardTypes) }
                    CreateRow(
                        cardName = cardType.name,
                        cardId = cardType.cardBrandId,
                        isChecked = isChecked
                    ) { checked ->
                        isChecked = checked
                        if (isChecked) {
                            selectedCardTypes = selectedCardTypes + cardType
                        } else {
                            selectedCardTypes = selectedCardTypes - cardType
                        }
                    }
                }
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
                        acceptedCards = selectedCardTypes
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
