package com.example.oneentrysdksample.network

import com.example.oneentry.model.OneEntryLocale
import com.example.oneentry.model.OneEntryMenu
import com.example.oneentry.network.ProjectService

class ProjectProvider {

    companion object {

        private val project = ProjectService.instance

        suspend fun getActiveLocale(): List<OneEntryLocale> {

            return project.locales()
        }

        suspend fun getMenu(): OneEntryMenu {

            return project.menu("bottom_bar")
        }
    }
}