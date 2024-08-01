package com.example.oneentrysdksample.items.homeItems.recent

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.oneentry.model.blocks.BlocksResult
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentrysdksample.Screen

@Composable
fun RecentBlock(
    blocks: BlocksResult?,
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

        blocks?.items?.forEach { block ->

            if (block.identifier.contains("recent")) {

                block.attributeValues?.get(locale.code)?.let { attribute ->

                    RecentBlockItem(
                        name = block.localizeInfos?.get(locale.code)?.title.toString(),
                        image = attribute["image"]?.asImage?.first()?.downloadLink.toString(),
                        sticker = attribute["sticker"]?.asList?.extended?.asImage?.downloadLink ?: "",
                        background = attribute["background"]?.asImage?.first()?.downloadLink.toString(),
                    ) {
                        val sticker = attribute["sticker"]?.asList?.value
                        navController.navigate(route = Screen.CatalogScreen.route + "/$sticker")
                    }
                }
            }
        }
    }
}