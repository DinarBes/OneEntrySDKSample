package com.example.oneentrysdksample.network

import android.util.Log
import com.example.oneentry.model.blocks.BlocksResult
import com.example.oneentry.model.blocks.OneEntryBlock
import com.example.oneentry.model.products.ProductsResult
import com.example.oneentry.network.BlocksService

class BlocksProvider private constructor() {

    companion object {

        private val blocks = BlocksService.instance

        suspend fun getBlocks(): BlocksResult {

            return blocks.blocks(langCode = "en_US")
        }

        suspend fun productsBlock(marker: String, langCode: String): ProductsResult {

            return blocks.products(marker = marker, langCode = langCode)
        }
    }
}