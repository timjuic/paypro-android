package com.found404.network.service

import com.found404.core.models.Terminal
import com.found404.network.result.AddingTerminalResult

interface TerminalService {
    suspend fun addTerminal(terminal: Terminal, mid: Int) : AddingTerminalResult
}