package com.example.oneentrysdksample.view.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.ui.theme.authViewText
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun RegistrationView() {

    val context = LocalContext.current

    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }

    val enabled = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Text(
            text = stringResource(id = R.string.auth_text),
            style = MaterialTheme.typography.titleSmall,
            color = authViewText,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        InfoUserView(
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            firstName = firstName,
            surname = surname
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = orange),
            enabled = enabled.value,
            onClick = {
                if (email.value.isEmpty() || password.value.isEmpty() || confirmPassword.value.isEmpty() || firstName.value.isEmpty() || surname.value.isEmpty()) {
                    enabled.value = false
                    Toast.makeText(context, "Fill in the fields", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else if (password.value != confirmPassword.value) {
                    enabled.value = false
                    Toast.makeText(context, "Password mismatch", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else {
                    enabled.value = true
                }
            }
        ) {
            Text(
                text = "SIGN UP",
                color = Color.White,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun InfoUserView(
    email: MutableState<String>,
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    firstName: MutableState<String>,
    surname: MutableState<String>,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        FormView(
            nameField = "Your e-mail",
            text = email
        ) {

        }
        PasswordView(
            nameField = "Create password",
            passwordState = password
        )
        PasswordView(
            nameField = "Confirm password",
            passwordState = confirmPassword
        )
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
    }
}

@Composable
fun FormView(
    nameField: String,
    text: MutableState<String>,
    action: () -> Unit
) {

    TextField(
        value = text.value,
        onValueChange = { newText ->
            text.value = newText
        },
        textStyle = MaterialTheme.typography.titleSmall,
        trailingIcon = {
            IconButton(onClick = { action() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = orange
                )
            }
        },
        label = {
            Text(
                text = nameField,
                style = MaterialTheme.typography.titleSmall,
                color = authViewText
            )
        },
        keyboardActions = KeyboardActions.Default,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = systemGrey,
            backgroundColor = Color.Transparent,
            cursorColor = Color.Gray,
            placeholderColor = Color.Gray,
            focusedIndicatorColor = Color.Gray
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordView(
    nameField: String,
    passwordState: MutableState<String>
) {

    val context = LocalContext.current
    val enabled = remember { mutableStateOf(false) }

    TextField(
        value = passwordState.value,
        onValueChange = { newText ->
            passwordState.value = newText
        },
        textStyle = MaterialTheme.typography.titleSmall,
        visualTransformation = if (enabled.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = {
                    if (passwordState.value.isNotEmpty()) {
                        enabled.value = !enabled.value
                    } else {
                        Toast.makeText(context, "Field is empty", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Icon(
                    painter = if (enabled.value) painterResource(id = R.drawable.open_eye) else painterResource(id = R.drawable.close_eye),
                    contentDescription = null,
                    tint = if (enabled.value) orange else systemGrey,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        label = {
            Text(
                text = nameField,
                style = MaterialTheme.typography.titleSmall,
                color = authViewText
            )
        },
        keyboardActions = KeyboardActions.Default,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = systemGrey,
            backgroundColor = Color.Transparent,
            cursorColor = Color.Gray,
            placeholderColor = Color.Gray,
            focusedIndicatorColor = Color.Gray
        ),
        modifier = Modifier.fillMaxWidth()
    )
}