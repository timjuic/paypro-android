package com.Found404.paypro.ui.pages

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.Found404.paypro.AuthCallbackImpl
import com.Found404.paypro.R
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.viewmodel.LoginProvidersViewModel
import com.found404.core.AppConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException


@Composable
fun WelcomePage(navController: NavController) {
    val loginProvidersViewModel: LoginProvidersViewModel = viewModel()
    val authModules = loginProvidersViewModel.authModules
    val authCallback = remember { AuthCallbackImpl(navController) }

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            loginProvidersViewModel.onGoogleSignInResult(result.data)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PayProHeadline(text = "PayPro", modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.weight(0.2f))

        PayProButton(
            text = "Sign Up",
            onClick = { navController.navigate("registration") },
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }

        // Load the buttons here
        authModules.forEach { authProvider ->
            val layoutId = authProvider.getButtonLayout(LocalContext.current)
            val buttonId = authProvider.getButtonId()
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp), // Adjust

                factory = { ctx ->
                    LayoutInflater.from(ctx).inflate(layoutId, null, false).apply {
                        findViewById<LinearLayout>(buttonId).setOnClickListener {
                            authProvider.onButtonClick(ctx, authCallback, signInLauncher)
//                            navController.navigate("login")
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

