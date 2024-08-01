package com.example.oneentrysdksample.network

import android.util.Log
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentry.model.project.OneEntryMenu
import com.example.oneentry.network.ProjectService

class ProjectProvider {

    companion object {

        private val project = ProjectService.instance

        suspend fun getActiveLocale(): List<OneEntryLocale> {

            Log.e("Locales: ", project.locales().toString())

            return project.locales()
        }

        suspend fun getMenu(): OneEntryMenu {

            return project.menu("bottom_bar")
        }
    }
}