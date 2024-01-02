package com.found404.paypro.login_email_password

import androidx.compose.runtime.Composable

interface AuthDisplayable {
    @Composable
    fun DisplayButton(authProviderClickListener: AuthProviderClickListener)
}