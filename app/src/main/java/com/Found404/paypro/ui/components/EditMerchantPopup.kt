import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.found404.core.models.MerchantResponse

@Composable
fun EditMerchantPopup(
    merchant: MerchantResponse,
    onSave: (MerchantResponse) -> Unit,
    onCancel: () -> Unit
) {
    var merchantName by remember { mutableStateOf(merchant.merchantName) }
    var city by remember { mutableStateOf(merchant.address.city) }
    var streetName by remember { mutableStateOf(merchant.address.streetName) }
    var postalCode by remember { mutableStateOf(merchant.address.postalCode.toString()) }

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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Postal Code") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(onClick = {
                    val updatedMerchant = merchant.copy(
                        merchantName = merchantName,
                        address = merchant.address.copy(
                            city = city,
                            streetName = streetName,
                            postalCode = postalCode.toInt()
                        )
                    )
                    onSave(updatedMerchant)
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
