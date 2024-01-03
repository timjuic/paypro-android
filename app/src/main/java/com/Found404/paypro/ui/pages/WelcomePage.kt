package com.Found404.paypro.ui.pages

import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.Found404.paypro.R
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.viewmodel.LoginProvidersViewModel
import com.found404.core.AppConfig


@Composable
fun WelcomePage(navController: NavController) {
    val loginProvidersViewModel: LoginProvidersViewModel = viewModel()
    val authModules = loginProvidersViewModel.authModules

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PayProHeadline(text = "PayPro", modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.weight(1f))

        PayProButton(
            text = "Sign Up",
            onClick = { navController.navigate("registration") }
        )

        Divider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(10.dp)
        )

        // Load the buttons here
        authModules.forEach { authProvider ->
            val layoutId = authProvider.getButtonLayout(LocalContext.current)
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { ctx ->
                    LayoutInflater.from(ctx).inflate(layoutId, null, false).apply {
                        findViewById<LinearLayout>(com.found404.paypro.login_google.R.id.myButton).setOnClickListener {
                            authProvider.onButtonClick(ctx)
                        }
                    }
                }
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
                navController.navigate("login")
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

