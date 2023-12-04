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
import androidx.compose.runtime.setValue
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

        Column(

        ) {
            var checkBoxVisa by remember { mutableStateOf(false) }
            var checkBoxMaster by remember { mutableStateOf(false) }
            var checkBoxMaestro by remember { mutableStateOf(false) }
            var checkBoxDiners by remember { mutableStateOf(false) }
            var checkBoxAmerican by remember { mutableStateOf(false) }

            Row {
                Checkbox(
                    checked = checkBoxVisa,
                    onCheckedChange = { checkBoxVisa = it }
                )
                Text(
                    text = "Visa",
                    modifier = Modifier.padding(15.dp)
                )
            }
            Row {
                Checkbox(
                    checked = checkBoxMaster,
                    onCheckedChange = { checkBoxMaster = it }
                )
                Text(
                    text = "MasterCard",
                    modifier = Modifier.padding(15.dp)
                )
            }
            Row {
                Checkbox(
                    checked = checkBoxMaestro,
                    onCheckedChange = { checkBoxMaestro = it }
                )
                Text(
                    text = "Maestro",
                    modifier = Modifier.padding(15.dp)
                )
            }
            Row {
                Checkbox(
                    checked = checkBoxDiners,
                    onCheckedChange = { checkBoxDiners = it }
                )
                Text(
                    text = "Diners Club",
                    modifier = Modifier.padding(15.dp)
                )
            }
            Row {
                Checkbox(
                    checked = checkBoxAmerican,
                    onCheckedChange = { checkBoxAmerican = it }
                )
                Text(
                    text = "American Express",
                    modifier = Modifier.padding(15.dp)
                )
            }

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
                    /*TODO*/
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

@Preview
@Composable
fun CardPayments() {
    CardPayments({})
}
