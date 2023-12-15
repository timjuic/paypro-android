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
fun PayProHeadline(
    text: String,
    modifier: Modifier = Modifier,
) {
    val customFontFamily = FontFamily(
        Font(R.font.montserrat_black, FontWeight.Black),
    )

    Text(
        text = text,
        fontSize = 50.sp,
        fontWeight = FontWeight.Black,
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        fontFamily = customFontFamily,
        modifier = modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
    )
}