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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.AuthServiceImpl
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.ui.components.PayProLabeledTextInput
import com.Found404.paypro.ui.components.PayProTitle
import kotlinx.coroutines.launch

@Composable
fun LoginPage(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authService = AuthServiceImpl()

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
        )

        Spacer(modifier = Modifier.weight(1f))

        PayProButton(
            text = "Login",
            onClick = {
                coroutineScope.launch {
                    val loginResult = authService.loginUser(email, password, context)
                    println(loginResult.success.toString() + " " + loginResult.data)

                    if (loginResult.success) {

                        navController.navigate("addingMerchants")
                    } else {
                        loginErrorMessage = loginResult.message ?: "Invalid Credentials!"
                        println("ERROR" + loginErrorMessage)
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

