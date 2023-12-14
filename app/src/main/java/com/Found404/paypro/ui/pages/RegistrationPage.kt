package com.Found404.paypro.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Found404.paypro.RegistrationServiceImpl
import com.Found404.paypro.ui.components.LabeledTextInput
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.Title


@Composable
fun RegisterScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var passwordRepeat by remember { mutableStateOf("")}

    val registrationService = RegistrationServiceImpl()
    val registrationValidator = registrationService.validator

    Column(modifier = Modifier.padding(16.dp)) {
        Title(text = "PayPro")

        LabeledTextInput(
            label = "Email",
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            placeholder = "John.Smith@gmail.com",
            validation = { email -> registrationValidator.validateEmail(email).success }
        )

        LabeledTextInput(
            label = "Password",
            value = password,
            onValueChange = { newPassword -> password = newPassword},
            validation = { email -> registrationValidator.validateWeakPassword(email).success }
        )

        LabeledTextInput(
            label = "Repeat Password",
            value = passwordRepeat,
            onValueChange = { newPassword -> passwordRepeat = newPassword},
            validation = { email -> registrationValidator.validateWeakPassword(email).success }
        )

        PayProButton(text = "Register", onClick = { /*TODO*/ })

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
            ) {
            Text(
                text = "Already have an account? Login",
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                )
        }

        PayProButton(text = "Sign Up with Google", onClick = { /*TODO*/ }, buttonColor = Color.Gray)

    }
}