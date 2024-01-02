package com.Found404.paypro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DeleteMerchantPopup(
    merchantId: Int,
    merchantName: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    additionalInfo: String, // Additional information entered by the user
    onAdditionalInfoChange: (String) -> Unit // Callback for additional info change
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Please confirm that you want to delete $merchantName.")
            Spacer(modifier = Modifier.height(8.dp))

            // TextInput field for additional information
            TextField(
                value = additionalInfo,
                onValueChange = { onAdditionalInfoChange(it) },
                label = { Text("Enter your full merchant name to delete") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Button(onClick = onConfirm) {
                Text("Confirm")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}