package com.example.oneentrysdksample.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.items.CategoryBlock
import com.example.oneentrysdksample.items.ContentBlock
import com.example.oneentrysdksample.items.MenuItem
import com.example.oneentrysdksample.items.RecentBlock
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeView(
    navController: NavController,
    mainViewModel: MainViewModel
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val menus by mainViewModel.menu.collectAsState()
    val blocks by mainViewModel.blocks.collectAsState()
    val locales by mainViewModel.locales.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerBody(
                items = listOf(

                    MenuItem(
                        route = Screen.HomeScreen.route,
                        idIcon = R.drawable.profile,
                        title = "My Profile"
                    ),
                    MenuItem(
                        route = Screen.HomeScreen.route,
                        idIcon = R.drawable.orders,
                        title = "My Orders"
                    ),
                    MenuItem(
                        route = Screen.HomeScreen.route,
                        idIcon = R.drawable.favorites,
                        title = "Favorites"
                    ),
                    MenuItem(
                        route = Screen.HomeScreen.route,
                        idIcon = R.drawable.payment,
                        title = "Payments"
                    ),
                    MenuItem(
                        route = Screen.HomeScreen.route,
                        idIcon = R.drawable.contact,
                        title = "Contact"
                    )
                ),
                itemTextStyle = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp,
                    color = systemGrey
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
            {

                navController.navigate(route = it.route)
            }
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(it)
        ) {

            locales?.first()?.let { locale ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                        .padding(top = 30.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {

                        IconButton(
                            onClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = systemGrey
                            )
                        }
                    }

                    mainViewModel.getBlocks()

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

                                    RecentBlock(
                                        name = block.localizeInfos[locale.code]?.title.toString(),
                                        image = attribute["image"]?.asImage?.first()?.downloadLink.toString(),
                                        background = attribute["background"]?.asImage?.first()?.downloadLink.toString(),
                                        sticker = attribute["sticker"]?.asList?.first()?.downloadLink.toString()
                                    ) {

                                        Log.e("Sticker: ", attribute["sticker"]?.asList?.first()?.downloadLink.toString())

                                        navController.navigate(route = Screen.CatalogScreen.route)
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {

                        blocks?.forEach { block ->

                            if (block.attributeSetId == null) {

                                CategoryBlock(name = block.localizeInfos[locale.code]?.title.toString()) {
                                    navController.navigate(route = Screen.CatalogScreen.route)
                                }
                            }
                        }
                    }

                    blocks?.forEach { block ->

                        if (block.attributeSetId == 9) {

                            ContentBlock(image = block.attributeValues?.get(locale.code)?.get("image")?.asImage?.first()?.downloadLink.toString()) {
                                navController.navigate(route = Screen.CatalogScreen.route)
                            }
                        }
                    }
                }
            }
        }
    }
}