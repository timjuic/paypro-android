package com.Found404.paypro.ui.pages

import android.os.Bundle
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.ui.components.PayProTextInput
import com.found404.core.models.ValidationLogic.MerchantDataValidator
import com.found404.core.models.MerchantViewModel
import com.found404.core.models.SharedPreferencesManager

@Composable
fun MerchantName(
    navController: NavController
) {
    var merchantName by remember { mutableStateOf( "") }
    var showErrorMessage by remember { mutableStateOf(false) }
    val merchantViewModel = MerchantViewModel()

    val validator = MerchantDataValidator()

    val bundle = Bundle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
//            .padding(16.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        PayProHeadline(
            text = "What's your merchant name?",
            textAlignment = TextAlign.Start,
            modifier = Modifier
                .padding(vertical = 50.dp, horizontal = 8.dp)
                .align(Alignment.Start)
        )
        PayProTextInput(
            value = merchantName,
            onValueChange = { newFullName -> merchantName = newFullName },
            placeholder = "Merchant name",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (showErrorMessage) {
            Text(
                text = "Please input a valid merchant name!",
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Box(modifier = Modifier.fillMaxSize()){
            val context = LocalContext.current
            PayProButton(
                text = "Next",
                onClick = {
                    if (validator.validateMerchantName(merchantName).success){
                        showErrorMessage = false
                        merchantViewModel.merchantData.fullName = merchantName

                        SharedPreferencesManager.saveMerchantName(context, merchantName)

                        navController.navigate("merchantAddress")
                    }
                    else {
                        showErrorMessage = true
                        Toast.makeText(
                            context,
                            validator.validateMerchantName(merchantName).errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
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
                onClick = { navController.navigate("addingMerchants") },
                buttonColor = Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .size(width = 150.dp, height = 70.dp)
            )
        }
    }
}
