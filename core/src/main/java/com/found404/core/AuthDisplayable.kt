package com.found404.core

import androidx.compose.runtime.Composable

interface AuthDisplayable {
    @Composable
    fun DisplayButton(authProviderClickListener: AuthProviderClickListener)
}