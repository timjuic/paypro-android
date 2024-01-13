package com.Found404.paypro.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PressForDurationIcon(
    imageVector: ImageVector,
    contentDescription: String,
    onLongPress: () -> Unit,
    resetPressState: Boolean,
    onResetPress: () -> Unit,
    onClick: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val pressState = interactionSource.collectIsPressedAsState()

    DisposableEffect(Unit) {
        if (resetPressState) {
            isPressed = false
            onResetPress()
        }
        onDispose { }
    }

    LaunchedEffect(resetPressState) {
        var pressedDuration = 0L
        println("Launched efekt je: " + resetPressState)
        while (true) {
            if (pressState.value) {
                pressedDuration += 100
                if (pressedDuration >= 1000) {
                    onLongPress()
                    isPressed = false
                    break
                }
            } else {
                pressedDuration = 0
            }
            delay(100)
        }
    }

    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }
            )
            .padding(8.dp),
        tint = if (isPressed)
            Color.Red
        else
            Color.Unspecified
    )
}