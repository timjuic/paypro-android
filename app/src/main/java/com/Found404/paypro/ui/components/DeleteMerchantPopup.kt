package com.Found404.paypro.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.found404.network.service.MerchantService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController

@Composable
fun DeleteMerchantPopup(
    merchantId: Int,
    merchantName: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onClose: (NavController) -> Unit,
    additionalInfo: String,
    onAdditionalInfoChange: (String) -> Unit,
    merchantService: MerchantService,
    navController: NavController
)  {
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            onCancel()
        },
        properties = DialogProperties(dismissOnClickOutside = false),
    ) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(width = 300.dp, height = 300.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Gray))
        {
            Column {
                PayProTitle(
                    "Please confirm that you want to delete $merchantName.",
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                PayProTextInput(
                    value = userInput,
                    onValueChange = { newValue ->
                        userInput = newValue
                        onAdditionalInfoChange(newValue)
                    },
                    placeholder = "Enter your full merchant name to delete",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    PayProButton(
                        text = "Cancel",
                        onClick = { onCancel() },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f)
                    )

                    PayProButton(
                        text = "Confirm",
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                val response =
                                    merchantService.deleteMerchant(merchantId, context)
                                withContext(Dispatchers.Main) {
                                    if (response?.success == true) {
                                        showToast = true
                                        showMessage = true
                                        onConfirm()
                                    } else {
                                        showToast = false
                                        onCancel()
                                    }
                                }
                            }
                        },
                        enabled = userInput == merchantName,
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                if (showToast && showMessage) {
                    MessagePopup(
                        message = "Merchant $merchantName was successfully deleted",
                        onDismiss = {
                            showToast = false
                            showMessage = false
                            onClose(navController)
                        }
                    )
                }
            }
        }
    }
}
