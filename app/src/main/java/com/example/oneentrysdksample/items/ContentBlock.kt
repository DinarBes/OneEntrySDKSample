package com.example.oneentrysdksample.items

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.oneentry.model.OneEntryBlock
import com.example.oneentry.model.OneEntryLocale
import com.example.oneentrysdksample.Screen

@Composable
fun ContentBlock(
    blocks: List<OneEntryBlock>?,
    locale: OneEntryLocale,
    navController: NavController
) {

    blocks?.forEach { block ->

        if (block.attributeSetId == 9) {

            ContentBlockItem(image = block.attributeValues?.get(locale.code)?.get("image")?.asImage?.first()?.downloadLink.toString()) {
                navController.navigate(route = Screen.CatalogScreen.route + "/${block.identifier}")
            }
        }
    }
}