package com.example.oneentrysdksample.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oneentry.model.attributes.OneEntryAttributeSet
import com.example.oneentry.model.common.OneEntryFilter
import com.example.oneentry.model.common.OneEntrySortDirection
import com.example.oneentry.model.pages.OneEntryPage
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.products.OneEntryProductStatus
import com.example.oneentry.model.products.ProductsResult
import com.example.oneentrysdksample.network.AttributesProvider
import com.example.oneentrysdksample.network.PagesProvider
import com.example.oneentrysdksample.network.ProductsProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor() : ViewModel() {

    private val _products = MutableStateFlow<ProductsResult?>(null)
    val products = _products.asStateFlow()

    private val _product = MutableStateFlow<OneEntryProduct?>(null)
    val product = _product.asStateFlow()

    private val _relatedProducts = MutableStateFlow<ProductsResult?>(null)
    val relatedProducts = _relatedProducts.asStateFlow()

    private val _attributeColors = MutableStateFlow<OneEntryAttributeSet?>(null)
    val attributeColors = _attributeColors.asStateFlow()

    private val _attributeStickers = MutableStateFlow<OneEntryAttributeSet?>(null)
    val attributeSticker = _attributeStickers.asStateFlow()

    private val _filterProducts = MutableStateFlow<ProductsResult?>(null)
    val filterProducts = _filterProducts.asStateFlow()

    private val _pages = MutableStateFlow<List<OneEntryPage>?>(null)
    val pages = _pages.asStateFlow()

    private val _productStatuses = MutableStateFlow<List<OneEntryProductStatus>?>(null)
    val productStatuses = _productStatuses.asStateFlow()

    fun getProductsPage(
        body: List<OneEntryFilter>,
        pageUrl: String
    ) {
        viewModelScope.launch {
            try {
                _products.value = ProductsProvider.getProductsPage(body, pageUrl)
            } catch (error: Exception) {
                Log.e("Get products page, vm error", error.toString())
            }
        }
    }

    fun getProducts(
        body: List<OneEntryFilter>,
        sortKey: String? = null,
        sortOrder: OneEntrySortDirection? = null
    ) {
        viewModelScope.launch {
            try {
                _products.value = ProductsProvider.getProducts(body, sortKey, sortOrder)
            } catch (error: Exception) {
                Log.e("Get products, vm error", error.toString())
            }
        }
    }

    fun getProduct(id: Int) {
        viewModelScope.launch {
            try {
                _product.value = ProductsProvider.getProduct(id)
            } catch (error: Exception) {
                Log.e("Get product by id, vm error", error.toString())
            }
        }
    }

    fun getRelatedProducts(id: Int) {
        viewModelScope.launch {
            try {
                _relatedProducts.value = ProductsProvider.getRelatedProducts(id)
            } catch (error: Exception) {
                Log.e("Get related product, vm error", error.toString())
            }
        }
    }

    fun getAttributeColor(
        locale: String
    ) {
        viewModelScope.launch {
            try {
                _attributeColors.value = AttributesProvider.getAttribute("product", "color", locale)
            } catch (error: Exception) {
                Log.e("Get attribute color, vm error", error.toString())
            }
        }
    }

    fun getAttributeSticker(
        locale: String
    ) {
        viewModelScope.launch {
            try {
                _attributeStickers.value = AttributesProvider.getAttribute("product", "sticker", locale)
            } catch (error: Exception) {
                Log.e("Get attribute color, vm error", error.toString())
            }
        }
    }

    fun getPagesChildren(
        url: String
    ) {
        viewModelScope.launch {
            try {
                _pages.value = PagesProvider.getPagesChildren(url)
            } catch (error: Exception) {
                Log.e("Get pages children, vm error", error.toString())
            }
        }
    }

    fun getProductStatuses() {
        viewModelScope.launch {
            try {
                _productStatuses.value = ProductsProvider.getProductStatuses()
            } catch (error: Exception) {
                Log.e("Get product statuses, vm error", error.toString())
            }
        }
    }
}