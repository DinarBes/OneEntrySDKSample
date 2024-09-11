package com.example.oneentrysdksample.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.products.OneEntryProductStatus
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.items.ProductItem
import com.example.oneentrysdksample.items.SearchBar
import com.example.oneentrysdksample.items.dropDown.SortedItem
import com.example.oneentrysdksample.items.homeItems.category.CategoryBlock
import com.example.oneentrysdksample.items.sheets.SheetFilter
import com.example.oneentrysdksample.items.sheets.SheetProduct
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel
import com.example.oneentrysdksample.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CatalogView(
    pageUrl: String,
    mainViewModel: MainViewModel,
    searchViewModel: SearchViewModel,
    catalogViewModel: CatalogViewModel,
    navController: NavController,
    productStatuses: List<OneEntryProductStatus>
) {

    val pages by mainViewModel.pages.collectAsState()
    val locales by mainViewModel.locales.collectAsState()

    val products by catalogViewModel.products.collectAsState()
    val filterProducts by catalogViewModel.filterProducts.collectAsState()

    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val content: @Composable (() -> Unit) = { Text("NULL") }
    var customSheetContent by remember { mutableStateOf(content) }

    val selectedItem = remember {
        mutableStateOf<OneEntryProduct?>(null)
    }

    locales?.forEach { locale ->
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetElevation = 8.dp,
            sheetBackgroundColor = Color.White,
            sheetContent = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (modalBottomSheetState.isVisible) {
                        customSheetContent()
                    }
                }
            }
        ) {

            val connection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        return super.onPreScroll(available, source)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
//                    .nestedScroll(connection),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        SortedItem(catalogViewModel = catalogViewModel)

                        Box(
                            modifier = Modifier
                                .clickable {
                                    customSheetContent = {
                                        SheetFilter(
                                            modalBottomSheetState = modalBottomSheetState,
                                            mainViewModel = mainViewModel,
                                            catalogViewModel = catalogViewModel,
                                            pageUrl = pageUrl
                                        )
                                    }
                                    scope.launch { modalBottomSheetState.show() }
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.filter),
                                    contentDescription = null,
                                    tint = systemGrey,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Filter",
                                    color = systemGrey,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }

                item {
                    SearchBar(
                        searchViewModel = searchViewModel,
                        mainViewModel = mainViewModel,
                        catalogViewModel = catalogViewModel,
                        productStatuses = productStatuses
                    )
                }

                item {
                    CategoryBlock(
                        pages = pages,
                        locale = locale,
                        navController = navController
                    )
                }

                if (filterProducts?.items?.isEmpty() == true || filterProducts?.items == null) {
                    products?.items?.let {
                        itemsIndexed(it) { _, item ->
                            if (item.id != 55) {
                                ProductItem(
                                    locale = locale,
                                    product = item,
                                    catalogViewModel = catalogViewModel,
                                    productStatuses = productStatuses
                                ) {
                                    selectedItem.value = item
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
                            }
                        }
                    }
//                    Toast.makeText(context, "No matching products", Toast.LENGTH_LONG).show()
                } else {
                    itemsIndexed(filterProducts?.items!!) { _, item ->
                        ProductItem(
                            locale = locale,
                            product = item,
                            catalogViewModel = catalogViewModel,
                            productStatuses = productStatuses
                        ) {
                            selectedItem.value = item
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
                    }
                }

//                item {
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        horizontalArrangement = Arrangement.spacedBy(20.dp),
//                        verticalArrangement = Arrangement.spacedBy(20.dp)
//                    ) {
//                        itemsIndexed(products) { _, item ->
//                            ProductItem(
//                                locale = locale,
//                                product = item,
//                                productStatuses = productStatuses
//                            ) {
//                                selectedItem.value = item
//                                customSheetContent = {
//                                    SheetProduct(
//                                        modalBottomSheetState = modalBottomSheetState,
//                                        catalogViewModel = catalogViewModel,
//                                        product = selectedItem.value!!,
//                                        locale = locale,
//                                        productStatuses = productStatuses
//                                    )
//                                }
//                                scope.launch { modalBottomSheetState.show() }
//                            }
//                        }
//                    }
//                }
            }
        }
    }
}