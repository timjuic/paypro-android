package com.Found404.paypro.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.Found404.paypro.ui.components.PayProNavigationDrawer

@Composable
fun AddingMerchants(
    navController: NavController
) {

    PayProNavigationDrawer(navController)

    Box(modifier = Modifier.fillMaxSize()){
        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
                .align(Alignment.BottomEnd)
                .zIndex(0f),
            onClick = {
                navController.navigate("merchantName")
            },
            shape = CircleShape,
            containerColor = Color.Blue,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}