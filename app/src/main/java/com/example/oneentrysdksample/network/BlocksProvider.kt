package com.example.oneentrysdksample.network

import com.example.oneentry.model.blocks.OneEntryBlock
import com.example.oneentry.model.common.OneEntryResult
import com.example.oneentry.model.products.OneEntryProduct
import com.example.oneentry.network.BlocksService

class BlocksProvider private constructor() {

    companion object {

        private val blocks = BlocksService.instance

        suspend fun getBlocks(): OneEntryResult<OneEntryBlock> {

            return blocks.blocks(langCode = "en_US")
        }

        suspend fun productsBlock(marker: String, langCode: String): OneEntryResult<OneEntryProduct> {

            return blocks.products(marker = marker, langCode = langCode)
        }
    }
}