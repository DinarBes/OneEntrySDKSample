package com.example.oneentrysdksample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.oneentrysdksample.config.Config
import com.example.oneentrysdksample.items.BottomNavigationItem
import com.example.oneentrysdksample.ui.theme.OneEntrySDKSampleTheme
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val context = App.applicationContext()
        val fileName = "new_certificate.p12"
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)
        val outputStream = FileOutputStream(File(context.filesDir, fileName))
        inputStream.copyTo(outputStream)

        inputStream.close()
        outputStream.close()

        Config.configure(context)

        setContent {

            val navController = rememberNavController()
            val mainViewModel = hiltViewModel<MainViewModel>()

            OneEntrySDKSampleTheme {

                val menu by mainViewModel.menu.collectAsState()
                val locales by mainViewModel.locales.collectAsState()

//                val items = mutableListOf<BottomNavigationItem>()
//                menu?.pages?.forEach { page -> }

                val items = listOf(
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.home),
                        unselectedIcon = painterResource(id = R.drawable.home),
                        hasNews = false,
                        route = Screen.HomeScreen.route
                    ),
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.catalog),
                        unselectedIcon = painterResource(id = R.drawable.catalog),
                        hasNews = false,
                        route = Screen.CatalogScreen.route
                    ),
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.favorites),
                        unselectedIcon = painterResource(id = R.drawable.favorites),
                        hasNews = false,
                        route = Screen.FavoritesScreen.route
                    ),
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.cart),
                        unselectedIcon = painterResource(id = R.drawable.cart),
                        hasNews = true,
                        badgeCount = 4,
                        route = Screen.MyOrdersScreen.route
                    ),
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.profile),
                        unselectedIcon = painterResource(id = R.drawable.profile),
                        hasNews = false,
                        route = Screen.ProfileScreen.route
                    )
                )

                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Scaffold(
//                        topBar = {
//                            TopAppBar(
//                                title = {
//                                    Box(modifier = Modifier.fillMaxWidth()) {
//                                        items.forEachIndexed { index, item ->
//                                            Row(
//                                                modifier = Modifier.fillMaxWidth(),
//                                                horizontalArrangement = Arrangement.SpaceBetween
//                                            ) {
//
//                                                Text(
//                                                    modifier = Modifier.padding(start = 20.dp),
//                                                    text = getLabelForRoute(navigationController.currentBackStackEntryAsState().value?.destination?.route),
//                                                    fontFamily = Lato,
//                                                    fontWeight = FontWeight.Medium,
//                                                    fontSize = 30.sp,
//                                                    color = systemGrey,
//                                                    maxLines = 1,
//                                                    overflow = TextOverflow.Ellipsis,
//                                                    textAlign = TextAlign.Center
//                                                )
//
//                                                IconButton(
//                                                    onClick = {
//                                                        navigationController.navigate(route = Screen.OrderScreen.route)
//                                                    }
//                                                ) {
//
//                                                    Icon(
//                                                        imageVector = Icons.Default.ShoppingCart,
//                                                        contentDescription = "Cart",
//                                                        tint = orange
//                                                    )
//                                                }
//                                            }
//                                        }
//                                    }
//                                },
//                                navigationIcon = {
//                                    if (navigationController.currentBackStackEntryAsState().value?.destination?.route != Screen.HomeScreen.route) {
//                                        IconButton(
//                                            modifier = Modifier
//                                                .offset(x = 15.dp)
//                                                .background(
//                                                    brush = Brush.linearGradient(
//                                                        colors = listOf(
//                                                            greyButton,
//                                                            greyButton
//                                                        )
//                                                    ),
//                                                    alpha = 1f,
//                                                    shape = RoundedCornerShape(16.dp)
//                                                )
//                                                .size(30.dp),
//                                            onClick = {
//
//                                                navigationController.popBackStack()
//                                            }
//                                        ) {
//
//                                            Icon(
//                                                modifier = Modifier.padding(5.dp),
//                                                painter = painterResource(id = R.drawable.back_arrow),
//                                                contentDescription = null,
//                                                tint = grey
//                                            )
//                                        }
//                                    }
//                                },
//                            )
//                        },
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(route = item.route)
                                        },
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (item.badgeCount != null) {
                                                        Badge {
                                                            Text(text = item.badgeCount.toString())
                                                        }
                                                    } else if (item.hasNews) {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    painter = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                                    tint = if (item.route.contains(navController.currentBackStackEntryAsState().value?.destination?.route.toString())) orange else systemGrey,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) {

                        Box(modifier = Modifier.padding(it)) {

                            NavigationGraph(
                                navHostController = navController,
                                mainViewModel = mainViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

