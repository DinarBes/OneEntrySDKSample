package com.example.oneentrysdksample.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.items.FavoriteProductItem
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavoritesView(
    catalogViewModel: CatalogViewModel,
    mainViewModel: MainViewModel,
) {

    val favoritesProducts = catalogViewModel.favoritesProducts
    val productStatuses = catalogViewModel.productStatuses.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (favoritesProducts.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.no_related),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = "No featured products",
                color = systemGrey,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            favoritesProducts.forEach { favoriteProduct ->
                productStatuses?.let {
                    FavoriteProductItem(
                        catalogViewModel = catalogViewModel,
                        mainViewModel = mainViewModel,
                        favoriteProduct = favoriteProduct,
                        productStatuses = it
                    )
                }
            }
        }
    }
}