package com.example.oneentrysdksample.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ContentBlockItem(
    image: String,
    action: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(10.dp)
            .clickable { action() }
    ) {
        AsyncImage(
            model = image,
            contentDescription = null
        )
    }
}