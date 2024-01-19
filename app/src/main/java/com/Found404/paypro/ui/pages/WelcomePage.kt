package com.Found404.paypro.ui.pages

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.Found404.paypro.AuthCallbackImpl
import com.Found404.paypro.AuthDependencyProvider
import com.Found404.paypro.R
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.viewmodel.LoginProvidersViewModel
import com.found404.core.auth.AuthCallbacks
import com.found404.core.auth.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun WelcomePage(navController: NavController) {
    val loginProvidersViewModel: LoginProvidersViewModel = viewModel()
    val authModules = loginProvidersViewModel.authModules
    var loginErrorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val authService = AuthDependencyProvider.getInstance().getAuthService()

    val authCallback = object : AuthCallbacks<LoginResponse> {
        override fun onSuccessfulLogin(response: LoginResponse) {
            coroutineScope.launch(Dispatchers.Main) {
                authService.saveLoggedInUser(response.data, context)
                navController.navigate("addingMerchants") {
                    popUpTo("welcome") {
                        inclusive = true
                    }
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
            val buttonLayout = authProvider.getButtonLayout(LocalContext.current)
            val buttonId = authProvider.getButtonId()
            authProvider.initializeState(authCallback)
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),

                factory = { ctx ->
                    LayoutInflater.from(ctx).inflate(buttonLayout, null, false).apply {
                        findViewById<LinearLayout>(buttonId).setOnClickListener {
                            authProvider.startActivity(ctx)
                        }
                    }
                }
            )
        }
    }
}

