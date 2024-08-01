package com.example.oneentrysdksample.items

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.ui.theme.authViewText
import com.example.oneentrysdksample.ui.theme.orange

@Composable
fun BottomAuthButton(
    navController: NavController,
    action: () -> Unit
) {

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = orange),
        onClick = {
            action()
        }
    ) {
        Text(
            text = "SIGN IN",
            color = Color.White,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Forgot Password?",
            color = authViewText,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Reset Password",
            color = orange,
            style = MaterialTheme.typography.titleSmall,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate(route = Screen.ForgotPasswordScreen.route)
            }
        )
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = orange,
                shape = RoundedCornerShape(30.dp)
            ),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        onClick = {
            navController.navigate(route = Screen.RegistrationScreen.route)
        }
    ) {
        Text(
            text = "CREATE AN ACCOUNT",
            color = orange,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp
        )
    }
}