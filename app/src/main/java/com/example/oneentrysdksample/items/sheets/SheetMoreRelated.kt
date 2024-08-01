package com.example.oneentrysdksample.items.sheets

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentrysdksample.viewmodel.CatalogViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetMoreRelated(
    products: List<OneEntryProduct>,
    locale: OneEntryLocale,
    catalogViewModel: CatalogViewModel
) {

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val selectedItem = remember {
        mutableStateOf<OneEntryProduct?>(null)
    }
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetElevation = 8.dp,
        sheetBackgroundColor = Color.White,
        sheetContent = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (modalBottomSheetState.isVisible) {
//                    SheetProduct(
//                        modalBottomSheetState = modalBottomSheetState,
//                        catalogViewModel = catalogViewModel,
//                        product = selectedItem.value!!,
//                        locale = locale
//                    )
                }
            }
        }
    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .horizontalScroll(rememberScrollState()),
//            horizontalArrangement = Arrangement.Start,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            products.forEach { product ->
//                ProductItem(
//                    product = product,
//                    locale = locale,
//
//                ) {
//
//                }
//            }
//        }
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            horizontalArrangement = Arrangement.spacedBy(20.dp),
//            verticalArrangement = Arrangement.spacedBy(20.dp)
//        ) {
//            itemsIndexed(products) {_, item ->
//                ProductItem(
//                    product = item,
//                    locale = locale
//                ) {
//                    selectedItem.value = item
//                    scope.launch {
//                        modalBottomSheetState.show()
//                    }
//                }
//            }
//        }
    }
}