package com.example.oneentrysdksample.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun ContactUsView() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(vertical = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        val context = LocalContext.current
        val enabled = remember { mutableStateOf(true) }
        val readOnly = remember { mutableStateOf(true) }

        val email = remember { mutableStateOf("") }
        val firstName = remember { mutableStateOf("") }
        val surname = remember { mutableStateOf("") }
        val topic = remember { mutableStateOf("") }
        val text = remember { mutableStateOf("") }

        FormView(
            nameField = "E-mail",
            readOnly = readOnly.value,
            text = email
        ) {

        }
        FormView(
            nameField = "First name",
            readOnly = readOnly.value,
            text = firstName
        ) {

        }
        FormView(
            nameField = "Surname",
            readOnly = readOnly.value,
            text = surname
        ) {

        }
        FormView(
            nameField = "Topic",
            readOnly = readOnly.value,
            text = topic
        ) {

        }
        FormView(
            nameField = "Text",
            readOnly = readOnly.value,
            text = text
        ) {

        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = orange),
            enabled = enabled.value,
            onClick = {
                if (email.value.isEmpty() || firstName.value.isEmpty() || surname.value.isEmpty() || topic.value.isEmpty()) {
                    enabled.value = false
                    Toast.makeText(context, "Fill in the fields", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else {
                    Toast.makeText(context, "Send", Toast.LENGTH_SHORT).show()
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