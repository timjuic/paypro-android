package com.Found404.paypro.viewmodel

import androidx.lifecycle.ViewModel
import com.found404.core.AuthModule
import com.found404.core.AuthProvider
import org.reflections.Reflections

class LoginProvidersViewModel : ViewModel() {
    private val authModuleRegistry: DefaultAuthModuleRegistry = DefaultAuthModuleRegistry()

    val authModules: List<AuthProvider<*, *>> by lazy {
        authModuleRegistry.getModules()
    }
}

class DefaultAuthModuleRegistry {
    fun getModules(): List<AuthModule<*, *>> {
        val modules = mutableListOf<AuthModule<*, *>>()

        val packageName = "com.found404.paypro.login_email_password"

        val reflections = Reflections(packageName)
        val subTypes = reflections.getSubTypesOf(AuthModule::class.java)
        val value = 0

        for (clazz in subTypes) {
            try {
                val instance = clazz.getDeclaredConstructor().newInstance()
                modules.add(instance as AuthModule<*, *>)
            } catch (e: Exception) {
                // Handle instantiation exception
            }
        }

        return modules
    }
}

interface AuthModuleRegistry {
    fun getModules(): List<AuthProvider<*, *>>
}