package com.example.oneentrysdksample.items.homeItems.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ContentBlockItem(
    background: String,
    action: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clickable { action() },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = background,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}