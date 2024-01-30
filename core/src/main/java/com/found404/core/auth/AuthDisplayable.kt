package com.found404.core.auth

import android.content.Context

interface AuthDisplayable {
    fun startActivity(context: Context)
    fun getButtonLayout(context: Context): Int
    fun getButtonId(): Int
}