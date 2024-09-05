package com.example.oneentrysdksample.items.homeItems.content

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.oneentry.model.blocks.OneEntryBlock
import com.example.oneentry.model.common.OneEntryResult
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentrysdksample.Screen

@Composable
fun ContentBlock(
    blocks: OneEntryResult<OneEntryBlock>?,
    locale: OneEntryLocale,
    navController: NavController
) {

    blocks?.items?.forEach { block ->

        if (block.identifier.contains("content")) {

            ContentBlockItem(
                background = block.attributeValues?.get(locale.code)?.get("image")?.asImage?.first()?.downloadLink.toString()
            ) {
                navController.navigate(route = Screen.CatalogScreen.route + "/${block.identifier}")
            }
        }
    }
}