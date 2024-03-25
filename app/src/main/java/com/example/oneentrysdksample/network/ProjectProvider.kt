package com.example.oneentrysdksample.network

import android.util.Log
import com.example.oneentry.model.OneEntryBlock
import com.example.oneentry.model.OneEntryLocale
import com.example.oneentry.model.OneEntryMenu
import com.example.oneentry.network.OneEntryProject

class ProjectProvider {

    companion object {

        private val project = OneEntryProject.instance

        suspend fun getActiveLocale(): List<OneEntryLocale> {

            return project.locales()
        }

        suspend fun getMenu(): OneEntryMenu {

            return project.menu("bottom_bar")
        }

        suspend fun getBlocks(): List<OneEntryBlock> {

            Log.e("Blocks: ", project.blocks(langCode = "en_US").toString())

            return project.blocks(langCode = "en_US")
        }
    }
}