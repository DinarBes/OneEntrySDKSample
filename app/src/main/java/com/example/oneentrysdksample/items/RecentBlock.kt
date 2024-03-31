package com.example.oneentrysdksample.items

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphNavigator
import androidx.navigation.navArgument
import com.example.oneentry.model.OneEntryBlock
import com.example.oneentry.model.OneEntryLocale
import com.example.oneentrysdksample.Screen

@Composable
fun RecentBlock(
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

            if (block.attributeSetId == 8) {

                block.attributeValues?.get(locale.code)?.let { attribute ->

                    RecentBlockItem(
                        name = block.localizeInfos[locale.code]?.title.toString(),
                        image = attribute["image"]?.asImage?.first()?.downloadLink.toString(),
                        background = attribute["background"]?.asImage?.first()?.downloadLink.toString(),
                        sticker = attribute["sticker"]?.asList?.first()?.downloadLink.toString()
                    ) {

                        navController.navigate(route = Screen.CatalogScreen.route + "/${block.identifier}")
                    }
                }
            }
        }
    }
}