package com.example.oneentrysdksample.view

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.items.CartProductItem
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel

@Composable
fun CartView(
    catalogViewModel: CatalogViewModel,
    mainViewModel: MainViewModel
) {

    val cartProducts by catalogViewModel.cartProducts.collectAsState()
    val productStatuses by catalogViewModel.productStatuses.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        if (cartProducts.isNullOrEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.no_related),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "Cart is empty",
                color = systemGrey,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        } else {
            cartProducts?.forEach { cartProduct ->
                productStatuses?.let {
                    CartProductItem(
                        cartProduct = cartProduct,
                        mainViewModel = mainViewModel,
                        catalogViewModel = catalogViewModel,
                        productStatuses = it
                    )
                }
            }
        }
    }
}