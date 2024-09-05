package com.example.oneentrysdksample.items

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.products.OneEntryProductStatus
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentrysdksample.ui.theme.backgroundLightGray
import com.example.oneentrysdksample.ui.theme.backgroundProduct
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel

@Composable
fun ProductItem(
    catalogViewModel: CatalogViewModel,
    product: OneEntryProduct,
    locale: OneEntryLocale,
    productStatuses: List<OneEntryProductStatus>,
    action: () -> Unit
) {

    val context = LocalContext.current
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

                attribute["sticker"]?.asList?.extended?.asImage?.downloadLink?.let {
                    SvgImage(data = it, modifier = Modifier.size(25.dp))
                }

                IconButton(
                    onClick = {
                        enabled = !enabled
                        if (enabled) {
                            catalogViewModel.addFavoritesProduct(product)
                        }
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
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            product.localizeInfos?.get(locale.code)?.title?.let {
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

            productStatuses.forEach { productStatus ->
                if (productStatus.id == product.statusId) {
                    Button(
                        modifier = Modifier
                            .border(
                                width = 2.dp,
                                color = if (productStatus.id == 1) orange else lightGrey,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = backgroundProduct),
                        onClick = {
                            if (productStatus.id == 1) {

                            } else {
                                Toast.makeText(context, "This product is out of stock", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(
                            text = productStatus.localizeInfos?.get(locale.code)?.title.toString(),
                            color = if (productStatus.id == 1) orange else systemGrey,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}