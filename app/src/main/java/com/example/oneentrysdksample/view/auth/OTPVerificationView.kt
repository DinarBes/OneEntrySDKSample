package com.example.oneentrysdksample.view.auth

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneentrysdksample.ui.theme.authViewText
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange

@Composable
fun OTPVerificationView() {

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
        var otpCode by remember { mutableStateOf("") }
        val otpCount = remember { 6 }
        val interactionSource = remember { MutableInteractionSource() }

        Text(
            text = "Enter your OTP code here",
            color = authViewText,
            style = MaterialTheme.typography.titleMedium
        )
        BasicTextField(
            value = otpCode,
            onValueChange = {
                if (it.length <= 6) {
                    otpCode = it
                }
            },
            interactionSource = interactionSource,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(otpCount) { index ->
                        CharView(
                            index = index,
                            text = otpCode
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Did not receive the OTP? ",
                color = authViewText,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "RESEND",
                color = orange,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .clickable {

                    }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = orange),
            enabled = enabled.value,
            onClick = {
                if (otpCode.length != 6) {
                    enabled.value = false
                    Toast.makeText(context, "OTP code incorrect", Toast.LENGTH_SHORT).show()
                    enabled.value = true
                } else {
                    enabled.value = true
                }
            }
        ) {
            Text(
                text = "VERIFY NOW",
                color = Color.White,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .border(
                1.dp, when {
                    isFocused -> Color.Gray
                    else -> lightGrey
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        text = char,
        style = androidx.compose.material.MaterialTheme.typography.h4,
        color = if (isFocused) {
            lightGrey
        } else {
            Color.Gray
        },
        textAlign = TextAlign.Center
    )
}