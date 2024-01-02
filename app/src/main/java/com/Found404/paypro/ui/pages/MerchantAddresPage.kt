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
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProTextInput
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
        PayProTextInput(
            value = merchantCity,
            onValueChange = { merchantCity = it },
            placeholder = "City name"
        )
        PayProTextInput(
            value = merchantStreetName,
            onValueChange = { merchantStreetName = it },
            placeholder = "Street name"
        )
        PayProTextInput(
            value = if (merchantStreetNumber == 0) "" else merchantStreetNumber.toString(),
            onValueChange = { merchantStreetNumber = it.toIntOrNull() ?: 0 },
            placeholder = "Street number"
        )
        PayProTextInput(
            value = if (merchantPostalCode == 0) "" else merchantPostalCode.toString(),
            onValueChange = { merchantPostalCode = it.toIntOrNull() ?: 0 },
            placeholder = "Postal code"
        )

        if (showErrorMessage) {
            showErrorMessages(validator, merchantCity, merchantStreetNumber, merchantPostalCode, merchantStreetName)
            showTextField(validator, merchantCity, merchantStreetNumber, merchantPostalCode, merchantStreetName)
        }

        Box(modifier = Modifier.fillMaxSize()){
            val context = LocalContext.current
            PayProButton(
                text = "Next",
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
                buttonColor = Color.Blue,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .size(width = 150.dp, height = 70.dp)
            )
            PayProButton(
                text = "Previous",
                onClick = { navController.navigate("merchantName") },
                buttonColor = Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .size(width = 150.dp, height = 70.dp)
            )
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
