package com.Found404.paypro.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.AuthDependencyProvider
import com.Found404.paypro.R
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.ui.components.PayProLabeledTextInput
import com.Found404.paypro.ui.components.PayProTitle
import com.found404.core.AppConfig
import com.found404.core.AuthCallbacks
import com.found404.core.models.LoginCredentials
import com.found404.core.models.LoginResponse
import com.found404.paypro.login_email_password.CredentialsAuthProvider
import kotlinx.coroutines.launch

@Composable
fun LoginPage(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authService = AuthDependencyProvider.getInstance().getAuthService()
    val credentialsAuthProvider = CredentialsAuthProvider(AppConfig.BASE_URL)

    val coroutineScope = rememberCoroutineScope()
    var loginErrorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

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
        )

        PayProLabeledTextInput(
            label = "Password",
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.weight(1f))

        PayProButton(
            text = "Login",
            onClick = {
                coroutineScope.launch {
                    try {
                        val endpointPath = "/api/auth/login"
                        val loginCredentials = LoginCredentials(email, password)

                        val authCallbacks = object : AuthCallbacks<LoginResponse> {
                            override fun onSuccessfulLogin(response: LoginResponse) {
                                authService.saveLoggedInUser(response.data, context)
                                navController.navigate("addingMerchants") {
                                    popUpTo("welcome") {
                                        inclusive = true
                                    }
                                }
                            }

                            override fun onFailedLogin(response: LoginResponse) {
                                loginErrorMessage = response.message ?: "Invalid Credentials!"
                            }

                            override fun onServerUnreachable(error: Throwable) {
                                loginErrorMessage = context.getString(R.string.err_server_unreachable)
                            }
                        }

                        credentialsAuthProvider.loginUser(endpointPath, loginCredentials, authCallbacks)
                    } catch (e: Exception) {
                        println("An error occurred: ${e.message}")
                    }
                }
            },
            modifier = Modifier.padding(bottom = 15.dp)
        )

        loginErrorMessage?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

