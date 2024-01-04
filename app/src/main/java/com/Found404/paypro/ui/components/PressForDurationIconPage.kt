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
    onResetPress: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val pressState = interactionSource.collectIsPressedAsState()

    DisposableEffect(Unit) {
        if (resetPressState) {
            isPressed = false // Reset isPressed state when resetPressState changes
            onResetPress() // Callback to reset press state
        }
        onDispose { }
    }

    LaunchedEffect(resetPressState) {
        // This effect will trigger when resetPressState changes
        var pressedDuration = 0L
        println("Launched efekt je: " + resetPressState)
        while (true) {
            if (pressState.value) {
                pressedDuration += 100 // Increase by 100 milliseconds
                if (pressedDuration >= 1000) { // Check if pressed for 2 seconds
                    onLongPress()
                    isPressed = false // Reset isPressed state after 2 seconds
                    break
                }
            } else {
                pressedDuration = 0 // Reset duration if not pressed
            }
            delay(100)
        }
    }

    // Return the Icon with updated states
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { /* Handle regular click here if needed */ }
            )
            .padding(8.dp),
        tint = if (isPressed) // Optionally change the tint when pressed
            Color.Red
        else
            Color.Unspecified
    )
}