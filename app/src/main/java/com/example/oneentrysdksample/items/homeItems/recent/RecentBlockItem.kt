package com.example.oneentrysdksample.items.homeItems.recent

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.oneentrysdksample.items.SvgImage

@Composable
fun RecentBlockItem(
    name: String,
    image: String,
    sticker: String,
    background: String,
    action: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { action() }
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = background,
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(5.dp)
        ) {
            SvgImage(
                data = sticker,
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.Start)
            )
            AsyncImage(
                model = image,
                contentDescription = null
            )
            Text(
                text = name,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}