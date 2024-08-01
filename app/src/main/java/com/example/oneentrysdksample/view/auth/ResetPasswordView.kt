package com.example.oneentrysdksample.view.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.oneentrysdksample.ui.theme.authViewText
import com.example.oneentrysdksample.ui.theme.orange

@Composable
fun ResetPasswordView() {

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
        val newPassword = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }

        Text(
            text = "Enter new password and confirm",
            color = authViewText,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Left
        )
        PasswordView(
            nameField = "New password",
            passwordState = newPassword
        )
        PasswordView(
            nameField = "Confirm password",
            passwordState = confirmPassword
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = orange),
            enabled = enabled.value,
            onClick = {
                if (newPassword.value.isEmpty() || confirmPassword.value.isEmpty()) {
                    enabled.value = false
                    Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else if (newPassword.value != confirmPassword.value) {
                    enabled.value = false
                    Toast.makeText(context, "Password mismatch", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else {
                    enabled.value = true
                }
            }
        ) {
            Text(
                text = "CHANGE PASSWORD",
                color = Color.White,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
            )
        }
    }
}