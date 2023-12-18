package com.Found404.paypro.ui.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.ui.components.PayProButton
import com.Found404.paypro.ui.components.PayProHeadline
import com.Found404.paypro.ui.components.PayProLabeledDropdown
import com.Found404.paypro.ui.components.PayProLabeledTextInput
import com.Found404.paypro.ui.components.PayProTitle
import com.Found404.paypro.ui.theme.PurpleGrey40
import com.found404.ValidationLogic.TerminalDataValidator
import com.found404.core.models.enums.TerminalType
import com.found404.network.service.implementation.TerminalServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddingTerminal(navController: NavController, mid: Int = 2) {
    var tid by remember { mutableStateOf("") }
    val posTypes = enumValues<TerminalType>().map { it.name }
    var selectedPosType by remember { mutableStateOf( "") }
    val coroutineScope = CoroutineScope(Dispatchers.Default)

    val validator = TerminalDataValidator();
    val context = LocalContext.current
    val terminalService = TerminalServiceImpl(context)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        PayProHeadline(text = "PayPro")

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PurpleGrey40, shapes.medium)
                .padding(
                    top = 10.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        ) {
            PayProTitle(
                text = "New Terminal",
                fontSize = 30.sp
            )

            PayProLabeledTextInput(
                label = "Terminal Key",
                value = tid,
                onValueChange = {newTid -> tid = newTid},
                placeholder = "Eg. 001"
            )

            PayProLabeledDropdown(
                label = "Terminal Type",
                onItemSelected = {selectedType ->
                    selectedPosType = selectedType
                },
                items = posTypes)

            Spacer(modifier = Modifier.height(45.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PayProButton(
                    text = "Cancel",
                    onClick = { /*TODO*/ },
                    buttonColor = Color.DarkGray,
                    modifier = Modifier.width(150.dp)
                )

                PayProButton(
                    text = "Create",
                    onClick = {
                        val terminal = validator.createTerminal(tid, selectedPosType, context)
                        if(terminal != null) {
                            coroutineScope.launch {
                                try {
                                    val response = terminalService.addTerminal(terminal, mid)

                                    withContext(Dispatchers.Main) {
                                        if(response.success) {
                                            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                                        }
                                        else {
                                            Toast.makeText(context, response.errorMessage, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } catch (ex: Exception) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "An unknown message occurred!", Toast.LENGTH_SHORT).show()
                                    }
                                    ex.printStackTrace()
                                }
                            }
                        }
                    },
                    modifier = Modifier.width(150.dp)
                )
            }
        }
    }
}