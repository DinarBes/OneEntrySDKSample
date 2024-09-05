package com.example.oneentrysdksample.items

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.products.OneEntryProductStatus
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavoriteProductItem(
    catalogViewModel: CatalogViewModel,
    mainViewModel: MainViewModel,
    favoriteProduct: OneEntryProduct,
    productStatuses: List<OneEntryProductStatus>
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        mainViewModel.locales.value?.first()?.let { loc ->
            SmallProductItem(
                product = favoriteProduct,
                locale = loc,
                catalogViewModel = catalogViewModel,
                productStatuses = productStatuses
            )

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = favoriteProduct.localizeInfos?.get(loc.code)?.title.toString(),
                    color = systemGrey,
                    style = MaterialTheme.typography.titleMedium
                )
                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = orange
                    )
                    favoriteProduct.attributeValues?.get(loc.code)?.get("rate")?.asDouble?.toString()
                        ?.let { it1 ->
                            Text(
                                text = it1,
                                color = systemGrey,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                }
                Text(
                    text = "$ ${favoriteProduct.price}",
                    color = systemGrey,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        catalogViewModel.removeFavoritesProduct(product = favoriteProduct)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        tint = systemGrey
                    )

                }
                IconButton(
                    onClick = {
                        catalogViewModel.addProductInCart(product = favoriteProduct)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cart),
                        contentDescription = null,
                        tint = orange
                    )
                }
            }
        }
    }
    Divider(
        thickness = 1.dp,
        color = lightGrey,
        modifier = Modifier.fillMaxWidth()
    )
}