package com.Found404.paypro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.found404.core.models.MerchantResponse
import com.found404.network.service.MerchantService

@Composable
fun MerchantItem(merchant: MerchantResponse, onDeleteMerchant: (Int) -> Unit, onDeleteTerminal: (String) -> Unit) {
    var showPopup by remember { mutableStateOf(false) }
    var showMerchantPopup by remember { mutableStateOf(false) }
    var selectedTerminalId by remember { mutableStateOf("") }
    var additionalInfo by remember { mutableStateOf("") }
    var resetPressState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
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

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = merchant.merchantName, color = Color.Black, style = TextStyle(fontSize = 30.sp))

                PressForDurationIcon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Merchant",
                    onLongPress = {
                        showMerchantPopup = true
                    },
                    resetPressState = resetPressState,
                    onResetPress = { resetPressState = !resetPressState }
                )
                println("Reres: " + resetPressState)
                println("onReset je: ")
            }

            Text(text = "${merchant.address.streetName}, ${merchant.address.city}")
            Text(text = "Street No: ${merchant.address.streetNumber}, Postal Code: ${merchant.address.postalCode}")

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

    if (showPopup) {
        DeleteTerminalPopup(
            terminalId = selectedTerminalId,
            onConfirm = {
                onDeleteTerminal(selectedTerminalId)
                showPopup = false
            },
            onCancel = { showPopup = false }
        )
    }
    if (showMerchantPopup) {
        DeleteMerchantPopup(
            merchantId = merchant.id,
            merchantName = merchant.merchantName,
            onConfirm = {
                onDeleteMerchant(merchant.id)
                showMerchantPopup = false
                resetPressState = !resetPressState
            },
            onCancel = {
                showMerchantPopup = false
                resetPressState = !resetPressState
            },
            additionalInfo = additionalInfo,
            onAdditionalInfoChange = { newInfo ->
                additionalInfo = newInfo
            },
            merchantService = MerchantService()
        )
    }
}