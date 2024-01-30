package com.found404.paypro.login_email_password.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.found404.paypro.login_email_password.R
import com.found404.paypro.login_email_password.auth.EmailLoginController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EmailLoginPage(emailLoginController: EmailLoginController) {
    val emailState = rememberInputState()
    val passwordState = rememberInputState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Header()
        LoginForm(emailState = emailState, passwordState = passwordState)
        Spacer(modifier = Modifier.weight(1f))
        LoginButton(
            emailState = emailState,
            passwordState = passwordState,
            coroutineScope = coroutineScope,
            emailLoginController
        )
    }
}

@Composable
fun Header() {
    val customFontFamily = FontFamily(
        Font(R.font.montserrat_black, FontWeight.Black),
    )

    Text(
        text = "PayPro",
        fontSize = 50.sp,
        fontWeight = FontWeight.Black,
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        fontFamily = customFontFamily,
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
    )
    Text(
        text = "Please login",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Left,
        fontFamily = customFontFamily,
        modifier = Modifier
            .padding(top = 8.dp, bottom = 10.dp)
            .fillMaxWidth()
    )
}

@Composable
fun LoginForm(emailState: InputState, passwordState: InputState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        EmailInputField(emailState)
        PasswordInputField(passwordState)
    }
}

@Composable
fun EmailInputField(emailState: InputState) {
    val customFontFamily = FontFamily(
        Font(R.font.montserrat_black, FontWeight.Black),
    )

    Text(
        text = "Your Email",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = customFontFamily,
        modifier = Modifier.padding(bottom = 3.dp, top = 8.dp)
    )

    OutlinedTextField(
        value = emailState.text,
        onValueChange = { newText: String ->
            emailState.text = newText
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Email") },
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            focusedBorderColor = if (emailState.isError) Color.Red else Color.Blue,
            unfocusedBorderColor = if (emailState.isError) Color.Red else Color.Black,
            errorBorderColor = Color.Red
        ),
        isError = emailState.isError
    )

    if (emailState.isError) {
        Text(
            text = "Invalid email format",
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun PasswordInputField(passwordState: InputState) {
    val customFontFamily = FontFamily(
        Font(R.font.montserrat_black, FontWeight.Black),
    )

    Text(
        text = "Your password",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = customFontFamily,
        modifier = Modifier.padding(bottom = 3.dp, top = 8.dp)
    )

    OutlinedTextField(
        value = passwordState.text,
        onValueChange = { newText: String ->
            passwordState.text = newText
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Password") },
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            focusedBorderColor = if (passwordState.isError) Color.Red else Color.Blue,
            unfocusedBorderColor = if (passwordState.isError) Color.Red else Color.Black,
            errorBorderColor = Color.Red
        ),
        visualTransformation = PasswordVisualTransformation(),
        isError = passwordState.isError
    )

    if (passwordState.isError) {
        Text(
            text = "Password is too short",
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun LoginButton(
    emailState: InputState,
    passwordState: InputState,
    coroutineScope: CoroutineScope,
    emailLoginController: EmailLoginController
) {
    val customFontFamily = FontFamily(
        Font(R.font.montserrat_black, FontWeight.Black),
    )
    val context = LocalContext.current

    OutlinedButton(
        onClick = {
            coroutineScope.launch {
                if (emailLoginController.validation(emailState, passwordState)) {
                    emailLoginController.performLogin(emailState.text, passwordState.text, context)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4D19E4)),
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = "Login",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = customFontFamily
        )
    }
}

class InputState(initial: String = "") {
    var text by mutableStateOf(initial)
    var isError by mutableStateOf(false)
}

@Composable
fun rememberInputState(): InputState {
    return remember { InputState() }
}