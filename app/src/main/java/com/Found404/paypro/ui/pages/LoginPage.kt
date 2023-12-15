package com.Found404.paypro.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.Found404.paypro.RegistrationServiceImpl
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.ui.components.PayProLabeledTextInput
import com.Found404.paypro.ui.components.PayProTitle

@Composable
fun LoginPage(navController: NavController) {
    var email by remember { mutableStateOf("TestEmail@gmail.com") }
    var password by remember { mutableStateOf("testing123") }

    val registrationService = RegistrationServiceImpl()
    val authValidator = registrationService.validator

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        PayProHeadline(text = "PayPro")
        PayProTitle(text = "Please login")

        PayProLabeledTextInput(
            label = "Your Email",
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            validation = { firstName -> authValidator.validateFirstName(firstName).success }
        )

        PayProLabeledTextInput(
            label = "Password",
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            validation = { lastName -> authValidator.validateFirstName(lastName).success }
        )

        Spacer(modifier = Modifier.weight(1f))

        PayProButton(
            text = "Login",
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(bottom = 15.dp)
        )
    }
}
