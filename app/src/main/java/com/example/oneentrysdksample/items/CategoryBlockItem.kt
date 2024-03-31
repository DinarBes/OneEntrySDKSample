package com.example.oneentrysdksample.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryBlockItem(
    name: String,
    action: () -> Unit
) {

    var enabled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                enabled = true
                action()
            }
            .background(
                color = if (enabled) Color.Black.copy(0.2f) else Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (enabled) Color.Black.copy(0.2f) else Color.Gray,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = if (enabled) Color.Black.copy(0.8f) else Color.Gray,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(10.dp)
        )
    }
}