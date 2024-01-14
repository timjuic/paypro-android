package com.Found404.paypro.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Please confirm that you want to delete $merchantName.")
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
                        .padding(vertical = 8.dp)
                )

                PayProButton(
                    text = "Confirm",
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val response = merchantService.deleteMerchant(merchantId, context)
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
                    enabled = userInput == merchantName
                )
                Spacer(modifier = Modifier.height(8.dp))

                PayProButton(
                    text = "Cancel",
                    onClick = {
                        showToast = false
                        onCancel()
                    }
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
