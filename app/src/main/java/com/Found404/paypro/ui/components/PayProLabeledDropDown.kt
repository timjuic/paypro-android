package com.Found404.paypro.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayProLabeledDropdown(
    label: String,
    onItemSelected: (String) -> Unit,
    items: List<String>,
    placeholder: String = "Please select an option",
    onFieldFocusChanged: (Boolean) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var isExpanded by remember { mutableStateOf(false) }
    var hasUserInteracted by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }


    val customFontFamily = FontFamily(
        Font(R.font.montserrat_bold, FontWeight.Bold),
    )

    Column(
        modifier = Modifier
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

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }
        ) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.LightGray,
                    focusedBorderColor = if (showError) Color.Red else Color.Blue,
                    unfocusedBorderColor = if (showError) Color.Red else Color.Black,
                    errorBorderColor = Color.Red
                ),
                placeholder = { Text(text = placeholder) },
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = visualTransformation,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedItem = item
                            isExpanded = false
                        }
                    )
                }
            }
        }

        if (showError) {
            Text(
                text = "Please select a valid ${label.lowercase(Locale.ROOT)}!",
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = customFontFamily,
                modifier = Modifier
                    .padding(top = 3.dp, bottom = 4.dp)
            )
        }
    }
}
