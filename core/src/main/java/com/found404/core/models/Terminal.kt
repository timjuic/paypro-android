package com.found404.core.models

import com.found404.core.models.enums.StatusType
import com.found404.core.models.enums.TerminalType

data class Terminal(
    var terminalKey: String,
    var type: Int,
    var status: Status
)
