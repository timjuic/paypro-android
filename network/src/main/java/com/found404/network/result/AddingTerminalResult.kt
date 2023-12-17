package com.found404.network.result

data class AddingTerminalResult(
    val success: Boolean,
    val message: String,
    val errorCode: Int? = null,
    val errorMessage: String? = null,
    val error: String? = null
)
