package com.example.oneentrysdksample.network

import com.example.oneentry.model.pages.OneEntryPage
import com.example.oneentry.network.PagesService

class PagesProvider {

    companion object {

        private val pages = PagesService.instance

        suspend fun getPages(): List<OneEntryPage> {

            return pages.pages("en_US")
        }

        suspend fun getPageByUrl(url: String): OneEntryPage {

            return pages.page(url, "en_US")
        }

        suspend fun getPagesChildren(
            url: String
        ): List<OneEntryPage> {

            return pages.pagesChildren(url = url, langCode = "en_US")
        }
    }
}