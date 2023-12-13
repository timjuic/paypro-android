package com.Found404.paypro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.ui.theme.PayProPurple

@Composable
fun PayProButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    buttonColor: Color = PayProPurple
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier
            .background(buttonColor)
            .fillMaxWidth()
            .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp)),
        colors = ButtonColors()
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

