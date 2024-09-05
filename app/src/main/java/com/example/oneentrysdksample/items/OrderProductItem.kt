package com.example.oneentrysdksample.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun OrderProductItem(
    product: OneEntryProduct,
    productPrice: MutableDoubleState,
    locale: OneEntryLocale
) {

    val countProduct = remember { mutableIntStateOf(1) }

    productPrice.doubleValue = product.price!! * countProduct.intValue

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        product.attributeValues?.get(locale.code)?.let { attribute ->
            AsyncImage(
                model = attribute["image"]?.asImage?.first()?.downloadLink,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = product.localizeInfos?.get(locale.code)?.title.toString(),
                color = systemGrey,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$ ${product.price}",
                color = systemGrey,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
        Counter(
            countProduct = countProduct
        )
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = null,
            tint = systemGrey,
            modifier = Modifier
                .clickable {

                }
        )
    }
}