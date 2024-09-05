package com.example.oneentrysdksample

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.oneentry.model.common.OneEntryConditionMarker
import com.example.oneentry.model.common.OneEntryFilter
import com.example.oneentrysdksample.view.CartView
import com.example.oneentrysdksample.view.auth.AuthPhoneView
import com.example.oneentrysdksample.view.auth.AuthView
import com.example.oneentrysdksample.view.CatalogView
import com.example.oneentrysdksample.view.FavoritesView
import com.example.oneentrysdksample.view.HomeView
import com.example.oneentrysdksample.view.LoadingView
import com.example.oneentrysdksample.view.OrderView
import com.example.oneentrysdksample.view.ProfileView
import com.example.oneentrysdksample.view.auth.AuthEmailView
import com.example.oneentrysdksample.view.auth.ForgotPasswordView
import com.example.oneentrysdksample.view.auth.OTPVerificationView
import com.example.oneentrysdksample.view.auth.RegistrationView
import com.example.oneentrysdksample.view.auth.ResetPasswordView
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel
import com.example.oneentrysdksample.viewmodel.SearchViewModel
import kotlinx.serialization.json.JsonPrimitive

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    searchViewModel: SearchViewModel,
    catalogViewModel: CatalogViewModel
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

        composable(route = Screen.AuthScreen.route) {

            AuthView(
                navController = navHostController
            )
        }

        composable(route = Screen.RegistrationScreen.route) {

            RegistrationView(
                navController = navHostController
            )
        }

        composable(route = Screen.AuthPhoneScreen.route) {

            AuthPhoneView(
                navController = navHostController
            )
        }

        composable(route = Screen.AuthEmailScreen.route) {

            AuthEmailView(
                navController = navHostController
            )
        }

        composable(route = Screen.ForgotPasswordScreen.route) {

            ForgotPasswordView(
                navController = navHostController
            )
        }

        composable(route = Screen.ResetPasswordScreen.route) {

            ResetPasswordView()
        }

        composable(route = Screen.OTPVerificationScreen.route) {

            OTPVerificationView()
        }

        composable(route = Screen.HomeScreen.route) {

            HomeView(
                navController = navHostController,
                mainViewModel = mainViewModel
            )
        }

        composable(
            route = Screen.CatalogScreen.route + "/{page_url}",
            arguments = listOf(navArgument("page_url") { type = NavType.StringType})
        ) {

            val pageUrl = it.arguments?.getString("page_url")
            val attributeStickers by catalogViewModel.attributeSticker.collectAsState()

            catalogViewModel.getAttributeSticker("en_US")

            attributeStickers?.listTitles?.forEach { sticker ->
                if (sticker.title == pageUrl) {
                    val body = OneEntryFilter(
                        attributeMarker = "sticker",
                        conditionMarker = OneEntryConditionMarker.IN,
                        conditionValue = JsonPrimitive(pageUrl),
                        pageUrl = listOf()
                    )
                    catalogViewModel.getProductsPage(listOf(body), pageUrl)
                    catalogViewModel.getProductStatuses()
                } else if (pageUrl == "all") {
                    catalogViewModel.getProducts(listOf())
                    catalogViewModel.getProductStatuses()
                } else {
                    pageUrl?.let { it1 ->
                        catalogViewModel.getProductsPage(listOf(), it1)
                    }
                    catalogViewModel.getProductStatuses()
                }
            }

            catalogViewModel.productStatuses.value?.let { statuses ->
                if (pageUrl != null) {
                    CatalogView(
                        pageUrl = pageUrl,
                        mainViewModel = mainViewModel,
                        searchViewModel = searchViewModel,
                        catalogViewModel = catalogViewModel,
                        navController = navHostController,
                        productStatuses = statuses
                    )
                }
            }
        }

        composable(route = Screen.FavoritesScreen.route) {

            FavoritesView(
                catalogViewModel = catalogViewModel,
                mainViewModel = mainViewModel
            )
        }

        composable(route = Screen.MyOrdersScreen.route) {

            OrderView(
                catalogViewModel = catalogViewModel,
                mainViewModel = mainViewModel
            )
//            CartView(
//                catalogViewModel = catalogViewModel,
//                mainViewModel = mainViewModel
//            )
        }

        composable(route = Screen.ProfileScreen.route) {

            ProfileView(
                navController = navHostController
            )
        }
    }
}