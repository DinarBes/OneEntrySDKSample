package com.example.oneentrysdksample.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.oneentrysdksample.items.SvgImage
import com.example.oneentrysdksample.items.homeItems.category.CategoryBlock
import com.example.oneentrysdksample.items.homeItems.content.ContentBlock
import com.example.oneentrysdksample.items.homeItems.recent.RecentBlock
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeView(
    navController: NavController,
    mainViewModel: MainViewModel
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val pages by mainViewModel.pages.collectAsState()
    val blocks by mainViewModel.blocks.collectAsState()
    val locales by mainViewModel.locales.collectAsState()

    locales?.first()?.let { locale ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            pages?.forEach { page ->

                if (page.pageUrl == "home_header") {

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        AsyncImage(
                            model = page.attributeValues?.get(locale.code)?.get("header")?.asImage?.first()?.downloadLink,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {

                            page.attributeValues?.get(locale.code)?.get("logo")?.asImage?.first()?.downloadLink?.let {
                                SvgImage(data = it, modifier = Modifier.size(150.dp).alpha(1f))
                            }

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
                    }
                }
            }

            RecentBlock(
                blocks = blocks,
                locale = locale,
                navController = navController
            )

            CategoryBlock(
                pages = pages,
                locale = locale,
                navController = navController
            )

            ContentBlock(
                blocks = blocks,
                locale = locale,
                navController = navController
            )
        }
    }

//    Scaffold(
//        scaffoldState = scaffoldState,
//        drawerContent = {
//            DrawerBody(
//                items = listOf(
//
//                    MenuItem(
//                        route = Screen.HomeScreen.route,
//                        idIcon = R.drawable.profile,
//                        title = "My Profile"
//                    ),
//                    MenuItem(
//                        route = Screen.HomeScreen.route,
//                        idIcon = R.drawable.orders,
//                        title = "My Orders"
//                    ),
//                    MenuItem(
//                        route = Screen.HomeScreen.route,
//                        idIcon = R.drawable.favorites,
//                        title = "Favorites"
//                    ),
//                    MenuItem(
//                        route = Screen.HomeScreen.route,
//                        idIcon = R.drawable.payment,
//                        title = "Payments"
//                    ),
//                    MenuItem(
//                        route = Screen.HomeScreen.route,
//                        idIcon = R.drawable.contact,
//                        title = "Contact"
//                    )
//                ),
//                itemTextStyle = TextStyle(
//                    fontFamily = FontFamily.Default,
//                    fontWeight = FontWeight.W400,
//                    fontSize = 16.sp,
//                    color = systemGrey
//                ),
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            )
//            {
//
//                navController.navigate(route = it.route)
//            }
//        }
//    ) {
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = MaterialTheme.colorScheme.background)
//                .padding(it)
//        ) {
//
//
//        }
//    }
}