package com.example.oneentrysdksample.network

import com.example.oneentry.model.OneEntryPage
import com.example.oneentry.network.PagesService

class PagesProvider {

    companion object {

        private val pages = PagesService.instance

        suspend fun getPageByUrl(url: String, langCode: String): OneEntryPage {

            return pages.page(url, langCode)
        }
    }
}