package com.found404.core

import android.content.Context

interface AuthDisplayable {
    fun onButtonClick(context: Context, authCallback: AuthCallback)
    fun getButtonLayout(context: Context): Int
    fun getButtonId(): Int
}