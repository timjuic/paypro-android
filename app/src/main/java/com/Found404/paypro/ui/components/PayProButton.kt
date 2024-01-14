package com.Found404.paypro.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.R
import com.Found404.paypro.ui.theme.PayProPurple

@Composable
fun PayProButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = PayProPurple,
    textColor: Color = Color.White,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true
) {
    val customFontFamily = FontFamily(
        Font(R.font.montserrat_bold, FontWeight.Bold),
    )

    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(15.dp),
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            leadingIcon?.let {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 12.dp),
                )
            }

            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = customFontFamily
            )
        }
    }
}
