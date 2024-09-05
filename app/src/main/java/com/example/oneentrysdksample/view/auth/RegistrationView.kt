package com.example.oneentrysdksample.view.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oneentry.network.AuthService
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.ui.theme.authViewText
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegistrationView(
    navController: NavController
) {

    val auth = AuthService.instance

    val context = LocalContext.current

    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val phone = remember { mutableStateOf("") }
    val mask = "+7 000 000 0000"
    val maskNumber = '0'

    val email = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }

    val enabled = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
            .verticalScroll(rememberScrollState()),
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
            surname = surname,
            phone = phone,
            mask = mask,
            maskNumber = maskNumber
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
                    CoroutineScope(Dispatchers.Main).launch {
                        auth.signUp(
                            marker = "email",
                            identifier = "user",
                            langCode = "en_US"
                        ) {

                            auth {
                                append("login", email.value)
                                append("password", password.value)
                            }

                            form {
                                append("email", email.value)
                                append("phone", maskNumber + phone.value)
                                append("name", firstName.value)
                                append("surname", surname.value)
                            }

                            notification {
                                this.email = email.value
                                this.phonePush = listOf()
                                this.phoneSMS = phone.value
                            }
                        }
                        navController.navigate(route = Screen.AuthScreen.route)
                    }
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
    phone: MutableState<String>,
    mask: String,
    maskNumber: Char,
    email: MutableState<String>,
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    firstName: MutableState<String>,
    surname: MutableState<String>,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        PhoneTextField(
            phone = phone,
            mask = mask,
            maskNumber = maskNumber
        )
        FormView(
            nameField = "Your e-mail",
            text = email,
            icon = Icons.Default.KeyboardArrowDown
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
            text = firstName,
            icon = Icons.Default.KeyboardArrowDown
        ) {

        }
        FormView(
            nameField = "Surname",
            text = surname,
            icon = Icons.Default.KeyboardArrowDown
        ) {

        }
    }
}

@Composable
fun FormView(
    nameField: String,
    text: MutableState<String>,
    readOnly: Boolean = false,
    icon: ImageVector? = null,
    action: () -> Unit
) {

    TextField(
        value = text.value,
        onValueChange = { newText ->
            text.value = newText
        },
        textStyle = MaterialTheme.typography.titleSmall,
        trailingIcon = {
            if (icon != null) {
                IconButton(onClick = { action() }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = orange
                    )
                }
            }
        },
        readOnly = readOnly,
        label = {
            Text(
                text = nameField,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W400),
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