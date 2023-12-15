package com.Found404.paypro.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun LabeledTextInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Enter your text",
    onFieldFocusChanged: (Boolean) -> Unit = {},
    validation: ((String) -> Boolean)? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var hasUserInteracted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(bottom = 4.dp, top = 12.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
                // Set hasUserInteracted to true when the user starts typing
                hasUserInteracted = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    onFieldFocusChanged(isFocused)

                    // Show error message only when unfocused, user has interacted, and there's a validation error
                    showError = !isFocused && hasUserInteracted && validation?.invoke(value) == false
                },
            placeholder = { Text(text = placeholder) },
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                disabledContainerColor = Color.LightGray,
                focusedBorderColor = if (showError) Color.Red else Color.Blue,
                unfocusedBorderColor = if (showError) Color.Red else Color.Black,
                errorBorderColor = Color.Red
            ),
        )

        // Show validation error message if there is one
        if (showError) {
            Text(
                text = "Please provide a valid ${label.lowercase(Locale.ROOT)}!",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
            )
        }
    }
}