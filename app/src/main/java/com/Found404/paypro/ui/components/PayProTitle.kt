package com.Found404.paypro.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.R

@Composable
fun PayProTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    val customFontFamily = FontFamily(
        Font(R.font.montserrat_bold, FontWeight.Bold),
    )

    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Left,
        fontFamily = customFontFamily,
        modifier = modifier
            .padding(top = 8.dp, bottom = 10.dp)
            .fillMaxWidth()
    )
}