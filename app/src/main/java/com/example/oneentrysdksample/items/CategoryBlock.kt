package com.example.oneentrysdksample.items

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.oneentry.model.OneEntryBlock
import com.example.oneentry.model.OneEntryLocale
import com.example.oneentrysdksample.Screen

@Composable
fun CategoryBlock(
    blocks: List<OneEntryBlock>?,
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

        blocks?.forEach { block ->

            if (block.attributeSetId == null) {

                CategoryBlockItem(name = block.localizeInfos[locale.code]?.title.toString()) {
                    navController.navigate(route = Screen.CatalogScreen.route + "/${block.identifier}")
                }
            }
        }
    }
}