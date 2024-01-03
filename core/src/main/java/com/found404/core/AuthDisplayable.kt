package com.found404.core

import android.content.Context
import androidx.compose.runtime.Composable

interface AuthDisplayable {
    fun onButtonClick(context: Context)
    fun getButtonLayout(context: Context): Int
}