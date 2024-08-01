package com.example.oneentrysdksample.network

import com.example.oneentry.model.common.OneEntryFilter
import com.example.oneentry.model.common.OneEntrySortDirection
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.model.products.OneEntryProductStatus
import com.example.oneentry.model.products.OneEntrySearchProduct
import com.example.oneentry.model.products.ProductsResult
import com.example.oneentry.network.ProductsService

class ProductsProvider {

    companion object {

        private val products = ProductsService.instance

        suspend fun getProducts(
            body: List<OneEntryFilter>,
            sortKey: String? = null,
            sortOrder: OneEntrySortDirection? = null
        ): ProductsResult {

            return products.products(
                body = body,
                langCode = "en_US",
                sortKey = sortKey,
                sortOrder = sortOrder
            )
        }

        suspend fun getProductsPage(
            body: List<OneEntryFilter>,
            pageUrl: String
        ): ProductsResult {

            return products.products(
                body = body,
                url = pageUrl,
                langCode = "en_US"
            )
        }

        suspend fun getProduct(id: Int): OneEntryProduct {

            return products.product(id = id, langCode = "en_US")
        }

        suspend fun getRelatedProducts(id: Int): ProductsResult {

            return products.relatedProducts(id = id, langCode = "en_US")
        }

        suspend fun searchProduct(name: String): List<OneEntrySearchProduct> {

            return products.quickSearch(name = name, langCode = "en_US")
        }

        suspend fun getProductStatuses(): List<OneEntryProductStatus> {

            return products.productStatuses(langCode = "en_US")
        }
    }
}