package com.example.oneentrysdksample.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oneentry.model.ProductModel
import com.example.oneentrysdksample.items.CategoryBlock
import com.example.oneentrysdksample.items.ProductItem
import com.example.oneentrysdksample.viewmodel.MainViewModel

@Composable
fun CatalogView(
    products: List<ProductModel>,
    mainViewModel: MainViewModel,
    navController: NavController
) {

    val blocks by mainViewModel.blocks.collectAsState()
    val locales by mainViewModel.locales.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        locales?.forEach { locale ->

            CategoryBlock(
                blocks = blocks,
                locale = locale,
                navController = navController
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                itemsIndexed(products) { _, item ->
                    ProductItem(
                        locale = locale,
                        product = item
                    ) {

                    }
                }
            }
        }
    }
}