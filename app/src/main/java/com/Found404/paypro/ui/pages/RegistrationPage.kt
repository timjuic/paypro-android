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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.RegistrationResult
import com.Found404.paypro.RegistrationServiceImpl
import com.Found404.paypro.ui.components.LabeledTextInput
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.Title
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun RegisterScreen(navController: NavController) {

    var email by remember { mutableStateOf("TestEmail@gmail.com") }
    var password by remember { mutableStateOf("testing123")}
    var passwordRepeat by remember { mutableStateOf("testing123")}
    var firstName by remember { mutableStateOf("Tester")}
    var lastName by remember { mutableStateOf("TesterLastName")}

    val registrationService = RegistrationServiceImpl()
    val registrationValidator = registrationService.validator

    var registrationResult by remember { mutableStateOf<RegistrationResult?>(null) }

    val coroutineScope = rememberCoroutineScope()
    var registrationErrorMessage by remember { mutableStateOf<String?>(null) }


    Column(modifier = Modifier.padding(16.dp)) {
        Title(text = "PayPro")

        LabeledTextInput(
            label = "First Name",
            value = firstName,
            onValueChange = { newFirstName   -> firstName = newFirstName },
            placeholder = "John",
            validation = { firstName -> registrationValidator.validateFirstName(firstName).success }
        )

        LabeledTextInput(
            label = "Last Name",
            value = lastName,
            onValueChange = { newLastName   -> lastName = newLastName },
            placeholder = "Smith",
            validation = { lastName -> registrationValidator.validateFirstName(lastName).success }
        )

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

        PayProButton(
            text = "Register",
            onClick = {
                coroutineScope.launch {
                    registrationResult = registrationService.registerUser(firstName, lastName, email, password)

                    withContext(Dispatchers.Main) {
                        if (registrationResult == null) {
                            registrationErrorMessage = "Backend server couldn't be reached. Please try again later"
                            return@withContext
                        }

                        if (!registrationResult!!.success) {
                            registrationErrorMessage = registrationResult!!.message
                            return@withContext
                        }

                        navController.navigate("addingMerchants")
                    }
                }
            }
        )


        registrationErrorMessage?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Button(
            onClick = {


            },
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
