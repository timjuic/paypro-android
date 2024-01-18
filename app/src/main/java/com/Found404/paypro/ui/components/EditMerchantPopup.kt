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
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProTextInput
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

    Dialog(onDismissRequest = onCancel) {
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
                PayProButton("Edit Merchant", onClick = {}, enabled = false)

                Spacer(modifier = Modifier.height(16.dp))

                PayProTextInput(
                    value = merchantName,
                    onValueChange = { merchantName = it },
                    placeholder = "Merchant Name"
                )

                Spacer(modifier = Modifier.height(8.dp))

                PayProTextInput(
                    value = city,
                    onValueChange = { city = it },
                    placeholder = "City"
                )

                Spacer(modifier = Modifier.height(8.dp))

                PayProTextInput(
                    value = streetName,
                    onValueChange = { streetName = it },
                    placeholder = "Street Name"
                )

                Spacer(modifier = Modifier.height(8.dp))

                PayProTextInput(
                    value = postalCode,
                    onValueChange = { postalCode = it },
                    placeholder = "Postal Code"
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

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PayProButton(text = "Save", onClick = {
                        val updatedMerchant = editMerchant.copy(
                            merchantName = merchantName,
                            address = editMerchant.address.copy(
                                city = city,
                                streetName = streetName,
                                postalCode = postalCode.toInt(),
                                streetNumber = streetNumber
                            ),
                            acceptedCards = selectedCardTypes,
                            status = selectedStatus
                        )
                        onConfirm(updatedMerchant)
                    })

                    PayProButton(text = "Cancel", onClick = onCancel)
                }
            }
        }
    }
}