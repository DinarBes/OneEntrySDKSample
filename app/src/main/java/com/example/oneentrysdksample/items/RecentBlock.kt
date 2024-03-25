package com.example.oneentrysdksample.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun RecentBlock(
    name: String,
    image: String,
    background: String,
    sticker: String,
    action: () -> Unit
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { action() },
        contentAlignment = Alignment.Center
    ) {

        AsyncImage(
            model = background,
            contentDescription = null
        )

        AsyncImage(
            model = sticker,
            contentDescription = null
        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = name,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}