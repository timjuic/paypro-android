package com.Found404.paypro.ui.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.AuthDependencyProvider
import com.Found404.paypro.AuthValidator
import com.Found404.paypro.R
import com.Found404.paypro.responses.RegistrationResponse
import com.Found404.paypro.ui.components.PayProLabeledTextInput
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.ui.components.PayProTitle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun RegisterPage(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var passwordRepeat by remember { mutableStateOf("")}
    var firstName by remember { mutableStateOf("")}
    var lastName by remember { mutableStateOf("")}

    val authService = AuthDependencyProvider.getInstance().getAuthService()

    var registrationResponse by remember { mutableStateOf<RegistrationResponse?>(null) }

    val coroutineScope = rememberCoroutineScope()
    var registrationErrorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val welcomePagePath = stringResource(id = R.string.welcome_page)

    Column(
        modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    ) {
        PayProHeadline(text = "PayPro")
        PayProTitle(text = "Please register your account")

        PayProLabeledTextInput(
            label = "First Name",
            value = firstName,
            onValueChange = { newFirstName   -> firstName = newFirstName },
            placeholder = "John",
            validation = { firstName -> AuthValidator.validateFirstName(firstName).success }
        )

        PayProLabeledTextInput(
            label = "Last Name",
            value = lastName,
            onValueChange = { newLastName   -> lastName = newLastName },
            placeholder = "Smith",
            validation = { lastName -> AuthValidator.validateFirstName(lastName).success }
        )

        PayProLabeledTextInput(
            label = "Email",
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            placeholder = "John.Smith@gmail.com",
            validation = { email -> AuthValidator.validateEmail(email).success }
        )

        PayProLabeledTextInput(
            label = "Password",
            value = password,
            onValueChange = { newPassword -> password = newPassword},
            validation = { email -> AuthValidator.validateWeakPassword(email).success },
            visualTransformation = PasswordVisualTransformation()
        )

        PayProLabeledTextInput(
            label = "Repeat Password",
            value = passwordRepeat,
            onValueChange = { newPassword -> passwordRepeat = newPassword},
            validation = { email -> AuthValidator.validateWeakPassword(email).success },
            imeAction = ImeAction.Done,
            visualTransformation = PasswordVisualTransformation()
        )

        PayProButton(
            text = "Register",
            onClick = {
                if (password != passwordRepeat) {
                    registrationErrorMessage = "Passwords do not match!"
                    return@PayProButton
                }

                coroutineScope.launch {
                    registrationResponse = authService.registerUser("/api/auth/register", firstName, lastName, email, password)

                    withContext(Dispatchers.Main) {
                        if (registrationResponse == null) {
                            registrationErrorMessage = "Backend server couldn't be reached. Please try again later"
                            return@withContext
                        }

                        if (!registrationResponse!!.success) {
                            registrationErrorMessage = registrationResponse!!.message
                            return@withContext
                        }

                        Toast.makeText(context, "You've successfully registered!", Toast.LENGTH_SHORT).show()

                        navController.navigate(welcomePagePath)
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

        val customFontFamily = FontFamily(
            Font(R.font.montserrat_bold, FontWeight.Bold),
        )

        val annotatedText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("Already have an account? ")
            }
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("Login")
            }
        }

        TextButton(
            onClick = {
                navController.navigate(welcomePagePath)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(bottom = 20.dp, top = 10.dp)
        ) {
            Text(
                text = annotatedText,
                fontWeight = FontWeight.Bold,
                fontFamily = customFontFamily,
                fontSize = 18.sp,
            )
        }
    }
}
