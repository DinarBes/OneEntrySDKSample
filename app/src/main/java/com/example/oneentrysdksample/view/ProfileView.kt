package com.example.oneentrysdksample.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.view.auth.FormView

@Composable
fun ProfileView() {

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
        val firstName = remember { mutableStateOf("") }
        val surname = remember { mutableStateOf("") }
        val topic = remember { mutableStateOf("") }
        val text = remember { mutableStateOf("") }

        FormView(
            nameField = "E-mail",
            text = email
        ) {

        }
        FormView(
            nameField = "First name",
            text = firstName
        ) {

        }
        FormView(
            nameField = "Surname",
            text = surname
        ) {

        }
        FormView(
            nameField = "Topic",
            text = topic
        ) {

        }
        FormView(
            nameField = "Text",
            text = text
        ) {

        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = orange),
            enabled = enabled.value,
            onClick = {
                if (email.value.isEmpty() || firstName.value.isEmpty() || surname.value.isEmpty() || topic.value.isEmpty() || text.value.isEmpty()) {
                    enabled.value = false
                    Toast.makeText(context, "Fill in the fields", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else {
                    enabled.value = true
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