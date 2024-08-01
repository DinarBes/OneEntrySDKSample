package com.example.oneentrysdksample.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.ui.theme.authViewText
import com.example.oneentrysdksample.ui.theme.backgroundButton
import com.example.oneentrysdksample.ui.theme.blue
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.ui.theme.yellow

@Composable
fun AuthView(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = backgroundButton,
                        shape = CircleShape
                    )
                    .padding(3.dp)
                    .clickable {

                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = systemGrey
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.oneentry),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = stringResource(id = R.string.auth_text),
            style = MaterialTheme.typography.titleLarge,
            color = authViewText,
            textAlign = TextAlign.Left
        )
        AuthButton(
            buttonColor = orange,
            icon = painterResource(id = R.drawable.email),
            text = "Login With Email",
            textColor = Color.White,
            borderColor = Color.Transparent
        ) {
            navController.navigate(route = Screen.AuthEmailScreen.route)
        }
        AuthButton(
            buttonColor = yellow, 
            icon = painterResource(id = R.drawable.phone),
            text = "Login With Phone",
            textColor = Color.White,
            borderColor = Color.Transparent
        ) {
            navController.navigate(route = Screen.AuthPhoneScreen.route)
        }
        AuthButton(
            buttonColor = blue,
            icon = painterResource(id = R.drawable.facebook),
            text = "Login With Facebook",
            textColor = Color.White,
            borderColor = Color.Transparent
        ) {

        }
        AuthButton(
            buttonColor = Color.White,
            icon = painterResource(id = R.drawable.google),
            text = "Login With Google",
            textColor = systemGrey,
            borderColor = lightGrey
        ) {

        }
        AuthButton(
            buttonColor = Color.White,
            icon = painterResource(id = R.drawable.profile),
            text = "CREATE AN ACCOUNT",
            textColor = orange,
            borderColor = orange
        ) {
            navController.navigate(route = Screen.RegistrationScreen.route)
        }
    }
}

@Composable
fun AuthButton(
    buttonColor: Color,
    icon: Painter,
    text: String,
    textColor: Color,
    borderColor: Color,
    action: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .background(
                color = buttonColor,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(30.dp)
            )
            .fillMaxWidth()
            .padding(15.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(color = Color.White)
            ) {
              action()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp
        )
    }
}