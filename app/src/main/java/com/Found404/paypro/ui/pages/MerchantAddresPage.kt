package com.Found404.paypro.ui.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.found404.ValidationLogic.MerchantDataValidator
import com.found404.core.models.Merchant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MerchantAddress(
    onButtonNextClick: () -> Unit,
    onButtonPrevClick: () -> Unit
) {

    var merchantModel by remember { mutableStateOf(Merchant()) }
    var showErrorMessage by remember { mutableStateOf(false) }

    val validator = MerchantDataValidator()

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
            text = "Enter your business address",
            fontSize = 50.sp,
            lineHeight = 50.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(
                vertical = 50.dp,
                horizontal = 20.dp
            )
        )
        TextField(
            singleLine = true,
            label = {Text("City name")},
            value = merchantModel.cityName,
            onValueChange = { newCityName ->
                merchantModel = merchantModel.copy(cityName = newCityName)
            },
        )
        TextField(
            singleLine = true,
            label = {Text("Street name")},
            value = merchantModel.streetName,
            onValueChange = { newStreetName ->
                merchantModel = merchantModel.copy(streetName = newStreetName)
            },
        )
        TextField(
            singleLine = true,
            label = { Text("Street number") },
            value = if (merchantModel.streetNumber == 0) "" else merchantModel.streetNumber.toString(),
            onValueChange = { newStreetNumber ->
                val parsedValue = newStreetNumber.toIntOrNull()
                if (parsedValue != null) {
                    merchantModel = merchantModel.copy(streetNumber = parsedValue)
                }
            }
        )

        TextField(
            singleLine = true,
            label = { Text("Postal code") },
            value = if (merchantModel.postCode == 0) "" else merchantModel.postCode.toString(),
            onValueChange = { newPostCode ->
                val parsedValue = newPostCode.toIntOrNull()
                if (parsedValue != null) {
                    merchantModel = merchantModel.copy(postCode = parsedValue)
                }
            }
        )

        if (showErrorMessage) {
            showErrorMessages(validator, merchantModel)
            showTextField(validator, merchantModel)
        }

        Box(modifier = Modifier.fillMaxSize()){
            val context = LocalContext.current
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
                    if (validate(merchantModel.cityName, merchantModel.streetName,
                            merchantModel.streetNumber, merchantModel.postCode)) {
                        showErrorMessage = true
                    } else {
                        showErrorMessage = false
                        onButtonNextClick()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text(text = "Next",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp)
                    .size(
                        width = 130.dp,
                        height = 60.dp),

                onClick = {
                    onButtonPrevClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text(text = "Previous",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun validate(cityName: String, streetName: String, streetNumber: Int, postCode: Int) : Boolean {
    return cityName.isBlank() || streetName.isBlank() || streetNumber == 0 || postCode == 0
}
@Composable
fun showErrorMessages(validator: MerchantDataValidator, merchantModel: Merchant) {
    val context = LocalContext.current
    if (!validator.validateCityName(merchantModel.cityName).success) {
        Toast.makeText(
            context,
            validator.validateCityName(merchantModel.cityName).errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    if (!validator.validateStreetName(merchantModel.streetName).success) {
        Toast.makeText(
            context,
            validator.validateStreetName(merchantModel.streetName).errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    if (!validator.validateStreetNumber(merchantModel.streetNumber).success) {
        Toast.makeText(
            context,
            validator.validateStreetNumber(merchantModel.streetNumber).errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    if (!validator.validatePostCode(merchantModel.postCode).success) {
        Toast.makeText(
            context,
            validator.validatePostCode(merchantModel.postCode).errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showTextField(validator: MerchantDataValidator, merchantModel: Merchant) {
    if (!validator.validateCityName(merchantModel.cityName).success) {
        Text(
            text = "Please input a valid city name!",
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    if (!validator.validateStreetName(merchantModel.streetName).success) {
        Text(
            text = "Please input a valid street name!",
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    if (!validator.validateStreetNumber(merchantModel.streetNumber).success) {
        Text(
            text = "Please input a valid street number!",
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    if (!validator.validatePostCode(merchantModel.postCode).success) {
        Text(
            text = "Please input a valid postal code!",
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
@Preview
@Composable
fun MerchantAddressPreview() {
    MerchantAddress(
        onButtonNextClick = {},
        onButtonPrevClick = {}
    )
}
