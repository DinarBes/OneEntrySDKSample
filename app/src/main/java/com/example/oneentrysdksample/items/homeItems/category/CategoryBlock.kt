package com.example.oneentrysdksample.items.homeItems.category

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.oneentry.model.pages.OneEntryPage
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentrysdksample.Screen

@Composable
fun CategoryBlock(
    pages: List<OneEntryPage>?,
    locale: OneEntryLocale,
    navController: NavController
) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {

        pages?.forEach { page ->

            if (page.parentId == 2) {

                CategoryBlockItem(name = page.localizeInfos?.get(locale.code)?.title.toString()) {
                    navController.navigate(route = Screen.CatalogScreen.route + "/${page.pageUrl}")
                }
            }
        }
    }
}