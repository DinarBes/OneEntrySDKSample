package com.example.oneentrysdksample.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.products.OneEntryProductStatus
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentrysdksample.items.sheets.SheetProduct
import com.example.oneentrysdksample.ui.theme.backgroundProduct
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmallProductItem(
    product: OneEntryProduct,
    locale: OneEntryLocale,
    catalogViewModel: CatalogViewModel,
    productStatuses: List<OneEntryProductStatus>
) {

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val content: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(content) }
    val selectedItem = remember {
        mutableStateOf<OneEntryProduct?>(null)
    }
    val scope = rememberCoroutineScope()

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
                        locale = locale,
                        productStatuses = productStatuses
                    )
                }
                scope.launch {
                    modalBottomSheetState.show()
                }
            }
    )
    {

        product.attributeValues?.get(locale.code)?.let { attribute ->

            attribute["sticker"]?.asList?.extended?.asImage?.downloadLink?.let {
                SvgImage(data = it, modifier = Modifier.size(20.dp))
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
}