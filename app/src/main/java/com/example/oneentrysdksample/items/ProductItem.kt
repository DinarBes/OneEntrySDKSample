package com.example.oneentrysdksample.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.oneentry.model.OneEntryLocale
import com.example.oneentry.model.ProductModel
import com.example.oneentrysdksample.ui.theme.backgroundProduct
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun ProductItem(
    product: ProductModel,
    locale: OneEntryLocale,
    action: () -> Unit
) {

    var enabled by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .background(
                color = backgroundProduct,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(10.dp)
            .clickable { action() }
    ) {

        product.attributeValues?.get(locale.code)?.let { attribute ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = attribute["sticker"]?.asImage?.first()?.downloadLink,
                    contentDescription = null
                )

                IconButton(
                    onClick = {
                        enabled = true
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = if (enabled) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (enabled) orange else systemGrey
                    )
                }
            }

            AsyncImage(
                model = attribute["image"]?.asImage?.first()?.downloadLink,
                contentDescription = null
            )

            product.localizeInfos[locale.code]?.title?.let {
                Text(
                    text = it,
                    color = systemGrey,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            attribute["price"]?.asString?.let {
                Text(
                    text = "$ $it",
                    color = systemGrey,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            Button(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = orange,
                        shape = RoundedCornerShape(20.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = backgroundProduct),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "ADD TO CART",
                    color = orange,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}