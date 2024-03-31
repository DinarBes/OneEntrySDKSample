package com.example.oneentrysdksample

sealed class Screen(val route: String, val title: String) {

    data object LoadingScreen: Screen(route = "loading_screen", title = "Loading")

    data object HomeScreen: Screen(route = "home_screen", title = "Catalog")

    data object CatalogScreen: Screen(route = "catalog_screen", "Catalog")

    data object FavoritesScreen: Screen(route = "favorites_screen", "Favorites")

    data object MyOrdersScreen: Screen(route = "my_orders_screen", "Cart")

    data object ProfileScreen: Screen(route = "profile_screen", "Profile")
}