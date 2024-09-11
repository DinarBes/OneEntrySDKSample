package com.example.oneentrysdksample.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun SettingView(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SettingItem(
            action = {
                navController.navigate(route = Screen.ProfileScreen.route)
            },
            text = "Profile",
            image = Icons.Default.AccountCircle
        )
        SettingItem(
            action = {
                navController.navigate(route = Screen.ContactUsScreen.route)
            },
            text = "Contact Us",
            image = Icons.Default.Email
        )
    }
}

@Composable
fun SettingItem(
    action: () -> Unit,
    text: String,
    image: ImageVector
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                action()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W400),
                color = systemGrey
            )

            IconButton(
                onClick = {
                    action()
                }
            ) {
                Icon(
                    imageVector = image,
                    contentDescription = null,
                    tint = orange
                )
            }
        }
        Divider(
            thickness = 1.dp,
            color = Color.Gray.copy(0.8f)
        )
    }
}