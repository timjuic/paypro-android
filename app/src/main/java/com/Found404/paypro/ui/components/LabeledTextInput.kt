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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabeledTextInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Enter your text",
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
            .padding(bottom = 4.dp, top = 12.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            modifier = Modifier
                .fillMaxWidth(),
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
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Black,
                errorBorderColor = Color.Red
            ),
        )
    }
}