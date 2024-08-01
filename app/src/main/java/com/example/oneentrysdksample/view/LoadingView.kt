package com.example.oneentrysdksample.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.items.ErrorItem
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.viewmodel.MainViewModel

@Composable
fun LoadingView(
    mainViewModel: MainViewModel,
    navController: NavController
) {

    val blocks by mainViewModel.blocks.collectAsState()
    val pages by mainViewModel.pages.collectAsState()
    var dataError = false

    LaunchedEffect(key1 = blocks, key2 = pages) {
        if (mainViewModel.dataExist) {
            navController.navigate(route = Screen.HomeScreen.route) {
                popUpTo(Screen.LoadingScreen.route)
            }
        } else {
            try {
                mainViewModel.getBlocks()
                mainViewModel.getPages()
            } catch (error: Exception) {
                dataError = true
            }
        }
    }

    if (!dataError) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = orange)
        }
    } else {
        ErrorItem()
    }
}