package com.example.oneentrysdksample.network

import com.example.oneentry.model.ProductsResult
import com.example.oneentry.network.ProductsService

class ProductsProvider {

    companion object {

        private val products = ProductsService.instance

        suspend fun getProducts(): ProductsResult {

            return products.products(langCode = "en_US")
        }
    }
}