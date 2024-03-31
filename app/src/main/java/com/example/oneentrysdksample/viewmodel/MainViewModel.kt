package com.example.oneentrysdksample.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oneentry.model.OneEntryBlock
import com.example.oneentry.model.OneEntryLocale
import com.example.oneentry.model.OneEntryMenu
import com.example.oneentry.model.OneEntryPage
import com.example.oneentry.model.ProductsResult
import com.example.oneentrysdksample.network.BlocksProvider
import com.example.oneentrysdksample.network.PagesProvider
import com.example.oneentrysdksample.network.ProductsProvider
import com.example.oneentrysdksample.network.ProjectProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _menus = MutableStateFlow<OneEntryMenu?>(null)
    val menu = _menus.asStateFlow()

    private val _locales = MutableStateFlow<List<OneEntryLocale>?>(null)
    val locales = _locales.asStateFlow()

    private val _page = MutableStateFlow<OneEntryPage?>(null)
    val page = _page.asStateFlow()

    private val _blocks = MutableStateFlow<List<OneEntryBlock>?>(null)
    val blocks = _blocks.asStateFlow()

    private val _productsBlock = MutableStateFlow<ProductsResult?>(null)
    val productsBlock = _productsBlock.asStateFlow()

    private val _products = MutableStateFlow<ProductsResult?>(null)
    val products = _products.asStateFlow()

    fun getLocales() {
        viewModelScope.launch {
            try {
                _locales.value = ProjectProvider.getActiveLocale()
            } catch (error: Exception) {
                Log.e("Get locales, vm error", error.toString())
            }
        }
    }

    private fun getMenu() {
        viewModelScope.launch {
            try {
                _menus.value = ProjectProvider.getMenu()
            } catch (error: Exception) {
                Log.e("Get menu, vm error", error.toString())
            }
        }
    }

    fun getPageByUrl(url: String) {
        viewModelScope.launch {
            try {
                _page.value = locales.value?.first()?.code?.let {
                    PagesProvider.getPageByUrl(url, it)
                }
            } catch (error: Exception) {
                Log.e("Get page, vm error", error.toString())
            }
        }
    }

    fun getBlocks() {
        viewModelScope.launch {
            try {
                _blocks.value = BlocksProvider.getBlocks()
            } catch (error: Exception) {
                Log.e("Get blocks, vm error", error.toString())
            }
        }
    }

    fun getProductsBlock(marker: String) {
        viewModelScope.launch {
            try {
                _productsBlock.value =
                    locales.value?.first()?.code?.let { BlocksProvider.productsBlock(marker, it) }
            } catch (error: Exception) {
                Log.e("Get block products, vm error", error.toString())
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                _products.value = ProductsProvider.getProducts()
            } catch (error: Exception) {
                Log.e("Get products, vm error", error.toString())
            }
        }
    }
}