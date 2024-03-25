package com.example.oneentrysdksample

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oneentrysdksample.view.CatalogView
import com.example.oneentrysdksample.view.FavoritesView
import com.example.oneentrysdksample.view.HomeView
import com.example.oneentrysdksample.view.MyOrdersView
import com.example.oneentrysdksample.view.ProfileView
import com.example.oneentrysdksample.viewmodel.MainViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {

        composable(route = Screen.HomeScreen.route) {

            HomeView(
                navController = navHostController,
                mainViewModel = mainViewModel
            )
        }

        composable(route = Screen.CatalogScreen.route) {

            CatalogView()
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