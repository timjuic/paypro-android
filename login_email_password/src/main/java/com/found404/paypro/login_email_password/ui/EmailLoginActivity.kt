package com.found404.paypro.login_email_password.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.found404.paypro.login_email_password.auth.EmailLoginController

class EmailLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailLoginPage(EmailLoginController())
        }
    }
}