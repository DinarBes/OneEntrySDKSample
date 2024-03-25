package com.example.oneentrysdksample.network

import com.example.oneentry.model.ProductsResult
import com.example.oneentry.network.OneEntryBlocks
import com.example.oneentry.network.OneEntryProducts
import com.example.oneentry.network.OneEntryProject

class ProductsProvider {

    companion object {

        private val blocks = OneEntryBlocks.instance
        val products = OneEntryProducts.instance

        suspend fun productsBlock(marker: String, langCode: String): ProductsResult {

            return blocks.products(marker = marker, langCode = langCode)
        }
    }
}