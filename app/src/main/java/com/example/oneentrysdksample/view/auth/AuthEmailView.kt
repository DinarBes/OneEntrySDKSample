package com.example.oneentrysdksample.view.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oneentrysdksample.items.BottomAuthButton

@Composable
fun AuthEmailView(
    navController: NavController
) {

    val password = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        FormView(
            nameField = "User name",
            text = text
        ) {

        }
        PasswordView(
            nameField = "Password",
            passwordState = password
        )

        Spacer(modifier = Modifier.weight(1f))

        BottomAuthButton(
            navController = navController
        ) {

        }
    }
}