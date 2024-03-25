package com.example.oneentrysdksample

sealed class Screen(val route: String) {

    data object HomeScreen: Screen(route = "home_screen")

    data object CatalogScreen: Screen(route = "catalog_screen")

    data object FavoritesScreen: Screen(route = "favorites_screen")

    data object MyOrdersScreen: Screen(route = "my_orders_screen")

    data object ProfileScreen: Screen(route = "profile_screen")
}