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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.found404.ValidationLogic.MerchantDataValidator
import com.found404.core.models.MerchantViewModel
import com.found404.core.models.SharedPreferencesManager

@Composable
fun MerchantAddress(
    navController: NavController
) {

    var merchantCity by remember { mutableStateOf("") }
    var merchantPostalCode by remember { mutableStateOf(0) }
    var merchantStreetName by remember { mutableStateOf("") }
    var merchantStreetNumber by remember { mutableStateOf(0) }
    var showErrorMessage by remember { mutableStateOf(false) }

    val validator = MerchantDataValidator()
    val merchantViewModel = MerchantViewModel()

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
            value = merchantCity,
            onValueChange = { newCityName ->
                merchantCity =  newCityName
            },
        )
        TextField(
            singleLine = true,
            label = {Text("Street name")},
            value = merchantStreetName,
            onValueChange = { newStreetName ->
                merchantStreetName = newStreetName
            },
        )
        TextField(
            singleLine = true,
            label = { Text("Street number") },
            value = if (merchantStreetNumber == 0) "" else merchantStreetNumber.toString(),
            onValueChange = { newStreetNumber ->
                merchantStreetNumber = newStreetNumber.toIntOrNull() ?: 0
            }
        )

        TextField(
            singleLine = true,
            label = { Text("Postal code") },
            value = if (merchantPostalCode == 0) "" else merchantPostalCode.toString(),
            onValueChange = { newPostCode ->
                merchantPostalCode = newPostCode.toIntOrNull() ?: 0
            }
        )

        if (showErrorMessage) {
            showErrorMessages(validator, merchantCity, merchantStreetNumber, merchantPostalCode, merchantStreetName)
            showTextField(validator, merchantCity, merchantStreetNumber, merchantPostalCode, merchantStreetName)
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
                    if (validate(merchantCity, merchantStreetName,
                            merchantStreetNumber.toInt(), merchantPostalCode.toInt())) {
                        showErrorMessage = true
                    } else {
                        showErrorMessage = false
                        merchantViewModel.merchantData.cityName = merchantCity
                        merchantViewModel.merchantData.streetNumber = merchantStreetNumber.toInt()
                        merchantViewModel.merchantData.streetName = merchantStreetName
                        merchantViewModel.merchantData.postCode = merchantPostalCode.toInt()

                        SharedPreferencesManager.saveMerchantAddress(
                            context,
                            merchantCity,
                            merchantStreetName,
                            merchantStreetNumber.toInt(),
                            merchantPostalCode.toInt()
                        )

                        navController.navigate("cardPayments")
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
                    navController.navigate("merchantName")
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
fun showErrorMessages(
    validator: MerchantDataValidator,
    merchantCity: String,
    merchantStreetNumber: Int,
    merchantPostalCode: Int,
    merchantStreetName: String
) {
    val context = LocalContext.current
    if (!validator.validateCityName(merchantCity).success) {
        Toast.makeText(
            context,
            validator.validateCityName(merchantCity).errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    if (!validator.validateStreetName(merchantStreetName).success) {
        Toast.makeText(
            context,
            validator.validateStreetName(merchantStreetName).errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    if (!validator.validateStreetNumber(merchantStreetNumber).success) {
        Toast.makeText(
            context,
            validator.validateStreetNumber(merchantStreetNumber).errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    if (!validator.validatePostCode(merchantPostalCode).success) {
        Toast.makeText(
            context,
            validator.validatePostCode(merchantPostalCode).errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showTextField(
    validator: MerchantDataValidator,
    merchantCity: String,
    merchantStreetNumber: Int,
    merchantPostalCode: Int,
    merchantStreetName: String
) {
    if (!validator.validateCityName(merchantCity).success) {
        Text(
            text = "Please input a valid city name!",
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    if (!validator.validateStreetName(merchantStreetName).success) {
        Text(
            text = "Please input a valid street name!",
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    if (!validator.validateStreetNumber(merchantStreetNumber).success) {
        Text(
            text = "Please input a valid street number!",
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    if (!validator.validatePostCode(merchantPostalCode).success) {
        Text(
            text = "Please input a valid postal code!",
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
