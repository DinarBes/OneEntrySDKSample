package com.example.oneentrysdksample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.oneentrysdksample.config.Config
import com.example.oneentrysdksample.config.getLabelForRoute
import com.example.oneentrysdksample.items.BottomNavigationItem
import com.example.oneentrysdksample.items.SearchBar
import com.example.oneentrysdksample.ui.theme.OneEntrySDKSampleTheme
import com.example.oneentrysdksample.ui.theme.badgeOrange
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel
import com.example.oneentrysdksample.viewmodel.SearchViewModel
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
            val searchViewModel = hiltViewModel<SearchViewModel>()
            val catalogViewModel = hiltViewModel<CatalogViewModel>()

            OneEntrySDKSampleTheme {

                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

                val items = listOf(
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.home),
                        unselectedIcon = painterResource(id = R.drawable.home),
                        hasNews = false,
                        route = Screen.HomeScreen.route,
                        title = Screen.HomeScreen.title
                    ),
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.catalog),
                        unselectedIcon = painterResource(id = R.drawable.catalog),
                        hasNews = false,
                        route = Screen.CatalogScreen.route + "/all",
                        title = Screen.CatalogScreen.title
                    ),
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.favorites),
                        unselectedIcon = painterResource(id = R.drawable.favorites),
                        hasNews = false,
                        route = Screen.FavoritesScreen.route,
                        title = Screen.FavoritesScreen.title
                    ),
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.cart),
                        unselectedIcon = painterResource(id = R.drawable.cart),
                        hasNews = true,
                        badgeCount = 4,
                        route = Screen.MyOrdersScreen.route,
                        title = Screen.MyOrdersScreen.title
                    ),
                    BottomNavigationItem(
                        selectedIcon = painterResource(id = R.drawable.profile),
                        unselectedIcon = painterResource(id = R.drawable.profile),
                        hasNews = false,
                        route = Screen.ProfileScreen.route,
                        title = Screen.ProfileScreen.title
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
                        topBar = {
                            if (navController.currentBackStackEntryAsState().value?.destination?.route != Screen.HomeScreen.route && navController.currentBackStackEntryAsState().value?.destination?.route != Screen.AuthScreen.route) {
                                CenterAlignedTopAppBar(
                                    title = {
                                        Text(
                                            text = getLabelForRoute(navController.currentBackStackEntryAsState().value?.destination?.route),
                                            color = systemGrey,
                                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(
                                            onClick = {
                                                navController.popBackStack()
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    actions = {
                                        if (
                                            navController.currentBackStackEntryAsState().value?.destination?.route != Screen.RegistrationScreen.route &&
                                            navController.currentBackStackEntryAsState().value?.destination?.route != Screen.AuthPhoneScreen.route &&
                                            navController.currentBackStackEntryAsState().value?.destination?.route != Screen.AuthEmailScreen.route &&
                                            navController.currentBackStackEntryAsState().value?.destination?.route != Screen.ForgotPasswordScreen.route &&
                                            navController.currentBackStackEntryAsState().value?.destination?.route != Screen.ResetPasswordScreen.route &&
                                            navController.currentBackStackEntryAsState().value?.destination?.route != Screen.OTPVerificationScreen.route
                                            ) {
                                            Row {
                                                IconButton(
                                                    onClick = { /*TODO*/ }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Menu,
                                                        contentDescription = null
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    scrollBehavior = scrollBehavior
                                )
                            }
                        },
                        bottomBar = {
                            if (navController.currentBackStackEntryAsState().value?.destination?.route != Screen.AuthScreen.route &&
                                navController.currentBackStackEntryAsState().value?.destination?.route != Screen.RegistrationScreen.route &&
                                navController.currentBackStackEntryAsState().value?.destination?.route != Screen.AuthPhoneScreen.route &&
                                navController.currentBackStackEntryAsState().value?.destination?.route != Screen.AuthEmailScreen.route &&
                                navController.currentBackStackEntryAsState().value?.destination?.route != Screen.ForgotPasswordScreen.route &&
                                navController.currentBackStackEntryAsState().value?.destination?.route != Screen.ResetPasswordScreen.route &&
                                navController.currentBackStackEntryAsState().value?.destination?.route != Screen.ResetPasswordScreen.route
                                ) {
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
                                                            Badge(
                                                                contentColor = systemGrey,
                                                                containerColor = badgeOrange
                                                            ) {
                                                                Text(
                                                                    text = item.badgeCount.toString(),
                                                                    fontWeight = FontWeight.Bold
                                                                )
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
                        }
                    ) {

                        Box(modifier = Modifier.padding(it)) {

                            NavigationGraph(
                                navHostController = navController,
                                mainViewModel = mainViewModel,
                                searchViewModel = searchViewModel,
                                catalogViewModel = catalogViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

