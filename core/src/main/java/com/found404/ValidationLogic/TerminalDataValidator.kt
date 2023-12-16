package com.found404.ValidationLogic

import android.content.Context
import android.widget.Toast
import com.found404.core.models.Terminal
import com.found404.core.models.enums.StatusType
import com.found404.core.models.enums.TerminalType

class TerminalDataValidator {
    private val terminal_allowed_characters = """^[a-zA-Z0-9 \${'$'}&'@_-]{2,20}${'$'}""".toRegex()

    fun createTerminal(terminalKey: String, mid: Int, terminalType: String, context: Context): Terminal? {
        if(!validateKey(terminalKey)) {
            Toast.makeText(context, "Terminal key is invalid!", Toast.LENGTH_SHORT).show()
            return null
        }
        if(!validateTerminalType(terminalType)) {
            Toast.makeText(context, "Please select a terminal type!", Toast.LENGTH_SHORT).show()
            return null
        }

        return Terminal(
            terminalKey = terminalKey,
            merchant = mid,
            type = TerminalType.valueOf(terminalType),
            status = StatusType.Active
        )
    }

    private fun validateKey(terminalKey: String): Boolean {
        return terminal_allowed_characters.matches(terminalKey)
    }

    private fun validateTerminalType(terminalType: String): Boolean {
        return TerminalType.values().any { it.name == terminalType }
    }
}