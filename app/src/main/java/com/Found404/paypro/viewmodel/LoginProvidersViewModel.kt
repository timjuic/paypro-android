package com.Found404.paypro.viewmodel

import androidx.lifecycle.ViewModel
import com.found404.core.AppConfig
import com.found404.core.AuthModule
import com.found404.core.AuthProvider
import com.found404.paypro.login_email_password.CredentialsAuthProvider
import com.found404.paypro.login_google.GoogleAuthProvider
import org.reflections.Reflections
import org.reflections.scanners.Scanner
import org.reflections.scanners.Scanners
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import java.net.URL

class LoginProvidersViewModel : ViewModel() {
    val authModules: List<AuthModule<*, *>> by lazy {
        getModules()
    }

    private fun getModules(): List<AuthModule<*, *>> {
        val modules = mutableListOf<AuthModule<*, *>>()

        //modules.add(CredentialsAuthProvider(AppConfig.BASE_URL))
        modules.add(GoogleAuthProvider(AppConfig.BASE_URL))
        // ADD MORE MODULES AS NEEDED

        return modules
    }
}
