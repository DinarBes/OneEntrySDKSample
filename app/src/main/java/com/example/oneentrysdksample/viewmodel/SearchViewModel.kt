package com.example.oneentrysdksample.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oneentry.model.products.OneEntrySearchProduct
import com.example.oneentrysdksample.network.ProductsProvider
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SearchViewModel: ViewModel() {

    private val _result = MutableStateFlow<List<OneEntrySearchProduct>>(listOf())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    @OptIn(FlowPreview::class)
    val result = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_result) { text, result ->
            if (text.isBlank()) {
                result
            } else {
                try {
                    ProductsProvider.searchProduct(text)
                } catch (error: Exception) {
                    Log.e("SearchViewModel error", error.message.toString())
                    emptyList()
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(15000),
            _result.value
        )

    fun onSearchTextChange(text: String) {

        _searchText.value = text
    }
}