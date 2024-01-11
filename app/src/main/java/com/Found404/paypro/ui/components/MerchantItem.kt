import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.ui.components.DeleteMerchantPopup
import com.found404.core.models.EditMerchant
import com.found404.core.models.MerchantResponse
import com.found404.network.service.MerchantService

@Composable
fun MerchantItem(
    merchant: MerchantResponse,
    onDeleteMerchant: (Int) -> Unit,
    onDeleteTerminal: (String) -> Unit,
    onEditMerchant: (EditMerchant) -> Unit
) {
    val merchantEdit: EditMerchant
    var showPopup by remember { mutableStateOf(false) }
    var showMerchantPopup by remember { mutableStateOf(false) }
    var showEditMerchantPopup by remember { mutableStateOf(false) }
    var selectedTerminalId by remember { mutableStateOf("") }
    var additionalInfo by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = merchant.merchantName,
                    color = Color.Black,
                    style = TextStyle(fontSize = 30.sp)
                )
                Row {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Merchant",
                        modifier = Modifier
                            .clickable { showEditMerchantPopup = true }
                            .padding(8.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Merchant",
                        modifier = Modifier
                            .clickable { showMerchantPopup = true }
                            .padding(8.dp)
                    )
                }
            }
            if (merchant.terminals.isEmpty()) {
                Text("This merchant has no terminals.", style = TextStyle(fontSize = 16.sp))
            } else {
                merchant.terminals.forEach { terminal ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Terminal: ${terminal.terminalKey}")
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Terminal",
                            modifier = Modifier
                                .clickable {
                                    selectedTerminalId = terminal.terminalKey
                                    showPopup = true
                                }
                        )
                    }
                }
            }
        }
    }

    if (showMerchantPopup) {
        DeleteMerchantPopup(
            merchantId = merchant.id,
            merchantName = merchant.merchantName,
            onConfirm = {
                onDeleteMerchant(merchant.id)
                showMerchantPopup = false
            },
            onCancel = { showMerchantPopup = false },
            additionalInfo = additionalInfo,
            onAdditionalInfoChange = { newInfo -> additionalInfo = newInfo },
            merchantService = MerchantService()
        )
    }

    if (showEditMerchantPopup) {
        val editMerchant = EditMerchant(
            id = merchant.id,
            merchantName = merchant.merchantName,
            address = merchant.address,
            acceptedCards = merchant.acceptedCards,
            status = 1
        )

        EditMerchantPopup(
            editMerchant = editMerchant,
            onConfirm = { updatedMerchant ->
                onEditMerchant(updatedMerchant)
                showEditMerchantPopup = false
            },
            onCancel = { showEditMerchantPopup = false }
        )
    }
}
