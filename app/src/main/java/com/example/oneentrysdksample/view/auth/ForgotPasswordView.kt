package com.example.oneentrysdksample.view.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.ui.theme.authViewText
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun ForgotPasswordView(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        val context = LocalContext.current
        val enabled = remember { mutableStateOf(true) }
        val email = remember { mutableStateOf("") }

        Text(
            text = "Please enter your email address. You will receive a link to create a new password via email.",
            color = authViewText,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Left
        )
        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            label = {
                Text(
                    text = "Enter your email",
                    color = authViewText,
                    style = MaterialTheme.typography.titleSmall
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = systemGrey,
                backgroundColor = Color.Transparent,
                cursorColor = Color.Gray,
                placeholderColor = Color.Gray,
                focusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = orange),
            enabled = enabled.value,
            onClick = {
                if (email.value.isEmpty()) {
                    enabled.value = false
                    Toast.makeText(context, "Enter email", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else {
                    enabled.value = true
                    navController.navigate(route = Screen.ResetPasswordScreen.route)
                }
            }
        ) {
            Text(
                text = "SEND",
                color = Color.White,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
            )
        }
    }
}