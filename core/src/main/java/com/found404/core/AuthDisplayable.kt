package com.found404.core

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable

interface AuthDisplayable {
    fun onButtonClick(context: Context, signInLauncher: ActivityResultLauncher<Intent>)
    fun getButtonLayout(context: Context): Int
    fun getButtonId(): Int
}