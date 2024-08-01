package com.example.oneentrysdksample.items

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.products.OneEntryProductStatus
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel
import com.example.oneentrysdksample.viewmodel.SearchViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchBar(
    searchViewModel: SearchViewModel,
    mainViewModel: MainViewModel,
    catalogViewModel: CatalogViewModel,
    productStatuses: List<OneEntryProductStatus>
) {

    val products = mutableListOf<OneEntryProduct>()
    val searchOn = remember { mutableStateOf(false) }
    val searchText by searchViewModel.searchText.collectAsState()
    val resultSearch by searchViewModel.result.collectAsState()
    val isSearching by searchViewModel.isSearching.collectAsState()
    val locales by mainViewModel.locales.collectAsState()

    Column {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    BorderStroke(1.dp, Color.Gray),
                    RoundedCornerShape(20.dp)
                )
        ) {

            Column {

                Box(
                    modifier = Modifier
                        .padding(horizontal = 22.dp)
                        .fillMaxWidth()
                ) {

                    androidx.compose.animation.AnimatedVisibility(visible = searchOn.value) {

                        OutlinedTextField(
                            modifier = Modifier
                                .padding(start = 40.dp, end = 10.dp)
                                .fillMaxWidth(),
                            value = searchText,
                            onValueChange = searchViewModel::onSearchTextChange,
                            placeholder = { Text(text = "Search") },
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Gray,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                backgroundColor = Color.Transparent,
                                cursorColor = Color.Gray,
                                placeholderColor = Color.Gray
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    IconButton(
                        onClick = {

                            searchOn.value = !searchOn.value
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }
        }

        AnimatedVisibility(visible = searchOn.value) {

            if (isSearching) {
                CircularProgressIndicator(
                    color = orange
                )
            } else {

                resultSearch.forEach { searchModel ->
                    catalogViewModel.getProduct(searchModel.id)
                    catalogViewModel.product.value?.let { products.add(it) }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    items(products) {

                        Log.e("Item: ", it.toString())
                        locales?.forEach { locale ->
                            ProductItem(
                                product = it,
                                locale = locale,
                                productStatuses = productStatuses
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}