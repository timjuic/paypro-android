package com.found404.paypro.login_email_password

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class EmailLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailLoginPage()
        }
    }
}