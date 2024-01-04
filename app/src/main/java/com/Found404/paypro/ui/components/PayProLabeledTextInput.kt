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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.R
import java.util.Locale

@Composable
fun PayProLabeledTextInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Enter your text",
    onFieldFocusChanged: (Boolean) -> Unit = {},
    validation: ((String) -> Boolean)? = null,
    validationErrorMessage: String? = "Please provide a valid",
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier // Make the modifier optional with default value
) {
    var isFocused by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var hasUserInteracted by remember { mutableStateOf(false) }

    val customFontFamily = FontFamily(
        Font(R.font.montserrat_bold, FontWeight.Bold),
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = customFontFamily,
            modifier = Modifier
                .padding(bottom = 3.dp, top = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
                hasUserInteracted = true
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    onFieldFocusChanged(isFocused)
                    showError = !isFocused && hasUserInteracted && validation?.invoke(value) == false
                },
            placeholder = { Text(text = placeholder) },
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                disabledContainerColor = Color.LightGray,
                focusedBorderColor = if (showError) Color.Red else Color.Blue,
                unfocusedBorderColor = if (showError) Color.Red else Color.Black,
                errorBorderColor = Color.Red
            ),
            visualTransformation = visualTransformation
        )

        if (showError) {
            Text(
                text = "$validationErrorMessage ${label.lowercase(Locale.ROOT)}!",
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = customFontFamily,
                modifier = Modifier
                    .padding(top = 3.dp, bottom = 4.dp)
            )
        }
    }
}
