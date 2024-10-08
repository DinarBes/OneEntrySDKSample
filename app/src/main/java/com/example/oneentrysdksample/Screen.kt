package com.example.oneentrysdksample

sealed class Screen(val route: String, val title: String) {

    data object AuthScreen: Screen(route = "auth_screen", title = "Auth")

    data object RegistrationScreen: Screen(route = "registration_screen", title = "Sign up")

    data object AuthPhoneScreen: Screen(route = "auth_phone_screen", title = "Sign in")

    data object AuthEmailScreen: Screen(route = "auth_email_screen", title = "Sign in")

    data object ForgotPasswordScreen: Screen(route = "forgot_password_screen", title = "Forgot Password")

    data object ResetPasswordScreen: Screen(route = "reset_password_screen", title = "Reset Password")

    data object OTPVerificationScreen: Screen(route = "otp_verification_screen", title = "Verification")

    data object LoadingScreen: Screen(route = "loading_screen", title = "Loading")

    data object HomeScreen: Screen(route = "home_screen", title = "Catalog")

    data object CatalogScreen: Screen(route = "catalog_screen", title = "Catalog")

    data object FavoritesScreen: Screen(route = "favorites_screen", title = "Favorites")

    data object MyOrdersScreen: Screen(route = "my_orders_screen", title = "Placing an order")

    data object SettingScreen: Screen(route = "setting_screen", title = "Settings")

    data object ProfileScreen: Screen(route = "profile_screen", title = "Profile")

    data object ContactUsScreen: Screen(route = "contact_us_screen", title = "Contact Us")

    data object ProductScreen: Screen(route = "product_screen", title = "")
}