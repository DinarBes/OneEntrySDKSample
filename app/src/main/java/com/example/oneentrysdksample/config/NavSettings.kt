package com.example.oneentrysdksample.config

import com.example.oneentrysdksample.Screen

fun getLabelForRoute(route: String?): String {

    when (route) {

        Screen.RegistrationScreen.route -> return Screen.RegistrationScreen.title
        Screen.AuthPhoneScreen.route -> return Screen.AuthPhoneScreen.title
        Screen.AuthEmailScreen.route -> return Screen.AuthEmailScreen.title
        Screen.ForgotPasswordScreen.route -> return Screen.ForgotPasswordScreen.title
        Screen.ResetPasswordScreen.route -> return Screen.ResetPasswordScreen.title
        Screen.OTPVerificationScreen.route -> return Screen.OTPVerificationScreen.title
        Screen.LoadingScreen.route -> return Screen.LoadingScreen.title
        Screen.HomeScreen.route -> return Screen.HomeScreen.title
        Screen.CatalogScreen.route -> return Screen.CatalogScreen.title
        Screen.FavoritesScreen.route -> return Screen.FavoritesScreen.title
        Screen.MyOrdersScreen.route -> return Screen.MyOrdersScreen.title
        Screen.ProfileScreen.route -> return Screen.ProfileScreen.title
    }

    return Screen.HomeScreen.title
}
