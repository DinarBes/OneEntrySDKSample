package com.example.oneentrysdksample.items

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.products.OneEntryProductStatus
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.items.dropDown.DropDownCart
import com.example.oneentrysdksample.items.sheets.SheetProduct
import com.example.oneentrysdksample.ui.theme.backgroundProduct
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartProductItem(
    cartProduct: OneEntryProduct,
    mainViewModel: MainViewModel,
    catalogViewModel: CatalogViewModel,
    productStatuses: List<OneEntryProductStatus>
) {

    val enabled = remember { mutableStateOf(false) }
    val countProduct = remember { mutableIntStateOf(0) }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val content: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(content) }
    val selectedItem = remember {
        mutableStateOf<OneEntryProduct?>(null)
    }
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        mainViewModel.locales.value?.first()?.let {

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .background(
                        color = backgroundProduct,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp)
                    .clickable {
                        customSheetContent = {
                            SheetProduct(
                                modalBottomSheetState = modalBottomSheetState,
                                catalogViewModel = catalogViewModel,
                                product = selectedItem.value!!,
                                locale = it,
                                productStatuses = productStatuses
                            )
                        }
                        scope.launch {
                            modalBottomSheetState.show()
                        }
                    }
            )
            {

                cartProduct.attributeValues?.get(it.code)?.let { attribute ->

                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter =
                                if (enabled.value)
                                    painterResource(id = R.drawable.yes_cart)
                                else
                                    painterResource(id = R.drawable.no_cart),
                            contentDescription = null,
                            tint = if (enabled.value) orange else orange.copy(alpha = 0.5f),
                            modifier = Modifier
                                .clickable {
                                    enabled.value = !enabled.value
                                }
                        )
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null,
                            tint = if (enabled.value) Color.White else Color.Transparent
                        )
                    }

                    AsyncImage(
                        model = attribute["image"]?.asImage?.first()?.downloadLink,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = cartProduct.localizeInfos?.get(it.code)?.title.toString(),
                    color = systemGrey,
                    style = MaterialTheme.typography.titleMedium
                )
                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = orange
                    )
                    cartProduct.attributeValues?.get(it.code)?.get("rate")?.asDouble?.toString()
                        ?.let { it1 ->
                            Text(
                                text = it1,
                                color = systemGrey,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                }
                Text(
                    text = "$ ${cartProduct.price}",
                    color = systemGrey,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                DropDownCart()

                Counter(
                    mainViewModel = mainViewModel,
                    countProduct = countProduct
                )
            }
        }
    }
    Divider(
        thickness = 1.dp,
        color = lightGrey,
        modifier = Modifier.fillMaxWidth()
    )
}