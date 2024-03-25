package com.example.oneentrysdksample.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.oneentry.model.ProductModel

@Composable
fun ProductItem(
    product: ProductModel,
    action: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { action() }
    ) {
        product.localizeInfos["en_US"]?.title?.let {

            Text(
                text = it
            )

        }
    }
}