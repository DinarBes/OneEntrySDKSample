package com.example.oneentrysdksample.network

import com.example.oneentry.model.OneEntryPage
import com.example.oneentry.network.OneEntryPages

class PagesProvider {

    companion object {

        private val pages = OneEntryPages.instance

        suspend fun getPageByUrl(url: String, langCode: String): OneEntryPage {

            return pages.page(url, langCode)
        }
    }
}