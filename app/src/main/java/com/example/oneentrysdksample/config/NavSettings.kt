package com.example.oneentrysdksample.config

import com.example.oneentrysdksample.Screen

fun getLabelForRoute(route: String?): String {

    when (route) {

        Screen.HomeScreen.route -> return Screen.HomeScreen.title
        Screen.CatalogScreen.route -> return Screen.CatalogScreen.title
        Screen.FavoritesScreen.route -> return Screen.FavoritesScreen.title
        Screen.MyOrdersScreen.route -> return Screen.MyOrdersScreen.title
        Screen.ProfileScreen.route -> return Screen.ProfileScreen.title
    }

    return Screen.HomeScreen.title
}
