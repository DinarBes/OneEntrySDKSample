package com.example.oneentrysdksample.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.oneentry.network.AuthService
import com.example.oneentry.network.UsersService
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.ui.theme.backgroundLightGray
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.view.auth.FormView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileView(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(vertical = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        val user = UsersService.instance

        val context = LocalContext.current
        val enabled = remember { mutableStateOf(true) }
        val enabledRedaction = remember { mutableStateOf(false) }
        val readOnly = remember { mutableStateOf(true) }
        val openDialog = remember { mutableStateOf(false) }

        val email = remember { mutableStateOf("") }
        val firstName = remember { mutableStateOf("") }
        val surname = remember { mutableStateOf("") }
        val phone = remember { mutableStateOf("") }

        Image(
            painter = painterResource(id = R.drawable.oneentry),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )
        FormView(
            nameField = "First name",
            readOnly = readOnly.value,
            text = firstName,
            icon = Icons.Default.KeyboardArrowDown
        ) {

        }
        FormView(
            nameField = "Surname",
            readOnly = readOnly.value,
            text = surname,
            icon = Icons.Default.KeyboardArrowDown
        ) {

        }
        FormView(
            nameField = "E-mail",
            readOnly = readOnly.value,
            text = email,
            icon = Icons.Default.KeyboardArrowDown
        ) {

        }
        FormView(
            nameField = "Phone",
            readOnly = readOnly.value,
            text = phone,
            icon = Icons.Default.KeyboardArrowDown
        ) {

        }
        FloatingActionButton(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                enabledRedaction.value = !enabledRedaction.value
                if (enabledRedaction.value) {
                    readOnly.value = false
                    Toast.makeText(context, "Edit information", Toast.LENGTH_SHORT).show()
                } else {
                    readOnly.value = true
                }
            },
            backgroundColor = backgroundLightGray,
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
        ) {
           Icon(
               modifier = Modifier
                   .size(25.dp),
               imageVector = Icons.Default.Create,
               contentDescription = null,
               tint = if (enabledRedaction.value) orange else systemGrey
           )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = orange),
            enabled = enabled.value,
            onClick = {
                if (email.value.isEmpty() || firstName.value.isEmpty() || surname.value.isEmpty() || phone.value.isEmpty()) {
                    enabled.value = false
                    Toast.makeText(context, "Fill in the fields", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else if (!enabledRedaction.value) {
                    if (openDialog.value) {
//                        PopUpCheck(
//                            navController = navController,
//                            openDialog = openDialog
//                        )
                    }
                } else if (enabledRedaction.value) {
                    CoroutineScope(Dispatchers.IO).launch {
//                        user.updateUser("en_US")
                    }
                }
            }
        ) {
            Text(
                text = if (enabledRedaction.value) "SAVE" else "LOG OUT",
                color = Color.White,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun PopUpCheck(
    navController: NavController,
    openDialog: MutableState<Boolean>
) {

    val auth = AuthService.instance

    Popup(
        alignment = Alignment.TopCenter,
        properties = PopupProperties()
    ) {
        Box(
            Modifier
                .padding(top = 5.dp)
                .background(lightGrey, RoundedCornerShape(15.dp))
                .border(1.dp, color = orange, RoundedCornerShape(15.dp))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Are you sure you want to log out of your account?",
                    color = orange,
                    modifier = Modifier.padding(vertical = 5.dp),
                    fontSize = 16.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
//                                auth.logout("", "")
                            }
                            navController.navigate(route = Screen.AuthScreen.route)
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        )
                    ) {
                        Text(
                            text = "Yes",
                            color = Color.White,
                        )
                    }
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = orange
                        )
                    ) {
                        Text(
                            text = "No",
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}