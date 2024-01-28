package com.Found404.paypro.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.R
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline

@Composable
fun MerchantCreated(
    navController: NavController
) {
    val addingMerchantsPath = stringResource(id = R.string.adding_merchants_page)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row {
            Icon(imageVector =
            Icons.Default.Check,
                contentDescription = "Success",
                tint = Color.White,
                modifier = Modifier
                    .size(
                        height = 200.dp,
                        width = 200.dp
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    )
                    .background(
                        color = Color.Blue,
                        shape = CircleShape
                    )
                )
        }
        Row {
            PayProHeadline(text = "Merchant Created!")
        }

        val customFontFamily = FontFamily(
            Font(R.font.montserrat_bold, FontWeight.Bold),
        )

        Row {
            Text(
                text = "Your merchant was successfully created!",
                fontSize = 20.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = customFontFamily,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    vertical = 70.dp,
                    horizontal = 20.dp
                )
            )
        }

        PayProButton(text = "Finish", onClick = { navController.navigate(addingMerchantsPath) })
    }
}