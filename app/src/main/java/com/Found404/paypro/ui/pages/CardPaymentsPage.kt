package com.Found404.paypro.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardPayments(
    onButtonFinishClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            text = "Select card payments that you accept",
            fontSize = 50.sp,
            lineHeight = 50.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(
                vertical = 100.dp,
                horizontal = 20.dp
            )
        )

        Column() {
            val checkBoxVisa by remember { mutableStateOf(false) }
            val checkBoxMaster by remember { mutableStateOf(false) }
            val checkBoxMaestro by remember { mutableStateOf(false) }
            val checkBoxDiners by remember { mutableStateOf(false) }
            val checkBoxAmerican by remember { mutableStateOf(false) }

            CreateRow(cardNameParam = "Visa", cardTypeParam = checkBoxVisa)
            CreateRow(cardNameParam = "MasterCard", cardTypeParam = checkBoxMaster)
            CreateRow(cardNameParam = "Maestro", cardTypeParam = checkBoxMaestro)
            CreateRow(cardNameParam = "Diners Club", cardTypeParam = checkBoxDiners)
            CreateRow(cardNameParam = "American Express", cardTypeParam = checkBoxAmerican)
            //make a for loop instead of hard calling the function every time
            //will do once database is live with all the card names
        }

        Box(modifier = Modifier.fillMaxSize()){
            Button(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    )
                    .size(
                        width = 130.dp,
                        height = 60.dp
                    ),
                onClick = {
                    onButtonFinishClick
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text(text = "Finish",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CreateRow(cardNameParam: String, cardTypeParam: Boolean){
    Row {
        Checkbox(
            checked = cardTypeParam,
            onCheckedChange = { cardType -> Unit}
        )
        Text(
            text = cardNameParam,
            modifier = Modifier.padding(15.dp)
        )
    }
}
@Preview
@Composable
fun CardPayments() {
    CardPayments({})
}
