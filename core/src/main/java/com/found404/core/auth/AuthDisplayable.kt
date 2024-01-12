package com.found404.core.auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable

interface AuthDisplayable {
    fun onButtonClick(context: Context, authCallback: AuthCallback, callbacks: AuthCallbacks<LoginResponse>)
    fun getButtonLayout(context: Context): Int
    fun getButtonId(): Int
}