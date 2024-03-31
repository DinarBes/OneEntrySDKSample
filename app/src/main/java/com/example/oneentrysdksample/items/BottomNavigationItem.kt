package com.example.oneentrysdksample.items

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem(
    val route: String,
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)
