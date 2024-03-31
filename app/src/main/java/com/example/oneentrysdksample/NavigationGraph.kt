package com.example.oneentrysdksample

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.oneentrysdksample.view.CatalogView
import com.example.oneentrysdksample.view.FavoritesView
import com.example.oneentrysdksample.view.HomeView
import com.example.oneentrysdksample.view.LoadingView
import com.example.oneentrysdksample.view.MyOrdersView
import com.example.oneentrysdksample.view.ProfileView
import com.example.oneentrysdksample.viewmodel.MainViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.LoadingScreen.route
    ) {

        composable(route = Screen.LoadingScreen.route) {

            LoadingView(
                mainViewModel = mainViewModel,
                navController = navHostController
            )
        }

        composable(route = Screen.HomeScreen.route) {

            HomeView(
                navController = navHostController,
                mainViewModel = mainViewModel
            )
        }

        composable(
            route = Screen.CatalogScreen.route + "/{block_id}",
            arguments = listOf(navArgument("block_id") { type = NavType.StringType})
        ) {

            val blockId = it.arguments?.getString("block_id")

            blockId?.let { marker -> mainViewModel.getProductsBlock(marker) }

            mainViewModel.productsBlock.value?.items?.let { products ->
                CatalogView(
                    products = products,
                    mainViewModel = mainViewModel,
                    navController = navHostController
                )
            }
        }

        composable(route = Screen.FavoritesScreen.route) {

            FavoritesView()
        }

        composable(route = Screen.MyOrdersScreen.route) {

            MyOrdersView()
        }

        composable(route = Screen.ProfileScreen.route) {

            ProfileView()
        }
    }
}