package com.example.oneentrysdksample.items.sheets

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.products.OneEntryProductStatus
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentrysdksample.items.NoDataItem
import com.example.oneentrysdksample.items.ProductItem
import com.example.oneentrysdksample.ui.theme.backgroundLightGray
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetProduct(
    modalBottomSheetState: ModalBottomSheetState,
    catalogViewModel: CatalogViewModel,
    product: OneEntryProduct,
    locale: OneEntryLocale,
    productStatuses: List<OneEntryProductStatus>
) {

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backgroundLightGray),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = product.attributeValues?.get(locale.code)?.get("image")?.asImage?.first()?.downloadLink,
                contentDescription = null,
                alignment = Alignment.Center
            )
        }
        InfoProduct(
            product = product,
            locale = locale
        )
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = orange),
            onClick = {
                catalogViewModel.addProductInCart(product = product)
                scope.launch {
                    modalBottomSheetState.hide()
                }
            }
        ) {
            Text(
                text = "ADD TO CART",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
        FeaturedProducts(
            id = product.id,
            catalogViewModel = catalogViewModel,
            locale = locale,
            productStatuses = productStatuses
        )
    }
}

@Composable
fun InfoProduct(
    product: OneEntryProduct,
    locale: OneEntryLocale
) {

    var sliderPosition: Float by remember { mutableFloatStateOf(0f) }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = product.localizeInfos?.get(locale.code)?.title.toString(),
            color = systemGrey,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.padding(end = 5.dp),
                text = "0",
                color = systemGrey,
                style = MaterialTheme.typography.bodyMedium
            )

            Slider(
                modifier = Modifier.fillMaxWidth(0.7f),
                value = sliderPosition,
                onValueChange = { sliderPosition = it.roundToInt().toFloat() },
                valueRange = 0f..100f,
                onValueChangeFinished = {

                },
                colors = SliderDefaults.colors(
                    thumbColor = orange,
                    activeTrackColor = orange,
                    inactiveTrackColor = Color.Black.copy(0.2f)
                )
            )

            TextField(
                value = String.format("%.0f", sliderPosition) + " units",
                onValueChange = { str ->

                    sliderPosition =
                        if ((str.filter { it.isDigit() }.toFloatOrNull() ?: 0f) <= 100f) {

                            str.filter { it.isDigit() }.toFloatOrNull() ?: 0f
                        }

                        else sliderPosition
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = systemGrey,
                    textColor = systemGrey,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                maxLines = 1,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Text(
            text = "$ ${product.price}",
            color = systemGrey,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = product.attributeValues?.get(locale.code)?.get("description")?.asText?.first()?.plainValue ?: "",
            color = lightGrey,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun FeaturedProducts(
    id: Int,
    catalogViewModel: CatalogViewModel,
    locale: OneEntryLocale,
    productStatuses: List<OneEntryProductStatus>
) {

    val context = LocalContext.current
    val moreProducts = remember { mutableStateOf(false) }
    val buttonActive = remember { mutableStateOf(true) }
    val relatedProducts by catalogViewModel.relatedProducts.collectAsState()

    catalogViewModel.getRelatedProducts(id)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Featured objects",
                color = systemGrey,
                style = MaterialTheme.typography.titleMedium
            )
            TextButton(
                onClick = {
                    if (buttonActive.value) {
                        moreProducts.value = true
                    } else {
                        Toast.makeText(context, "This product has no featured products", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(
                    text = "More",
                    color = if (buttonActive.value) { orange } else { orange.copy(alpha = 0.5f) },
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            relatedProducts?.items?.let { relatedProducts ->
                if (relatedProducts.isNotEmpty()) {
                    if (!moreProducts.value) {
                        relatedProducts.take(2).forEach { product ->
                            Log.e("Product: ", product.toString())
                            ProductItem(
                                product = product,
                                locale = locale,
                                catalogViewModel = catalogViewModel,
                                productStatuses = productStatuses
                            ) {

                            }
                        }
                    } else {
                        relatedProducts.forEach { product ->
                            Log.e("Product2: ", product.toString())
                            ProductItem(
                                product = product,
                                locale = locale,
                                catalogViewModel = catalogViewModel,
                                productStatuses = productStatuses
                            ) {

                            }
                        }
                    }
                } else {
                    buttonActive.value = false
                    NoDataItem(title = "This product has no featured products")
                }
            }
        }
    }
}