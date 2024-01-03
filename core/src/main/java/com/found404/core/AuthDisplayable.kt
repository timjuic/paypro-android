package com.found404.core

import android.content.Context
import androidx.compose.runtime.Composable

interface AuthDisplayable {
    @Composable
    fun DisplayButton(context: Context)
}