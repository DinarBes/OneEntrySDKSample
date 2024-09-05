package com.example.oneentrysdksample.view.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.items.BottomAuthButton
import com.example.oneentrysdksample.ui.theme.authViewText
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun AuthPhoneView(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        val phone = remember { mutableStateOf("") }
        val mask = "+7 000 000 0000"
        val maskNumber = '0'

        PhoneTextField(
            phone = phone,
            mask = mask,
            maskNumber = maskNumber
        )

        Spacer(modifier = Modifier.weight(1f))

        BottomAuthButton(
            navController = navController
        ) {
            navController.navigate(route = Screen.OTPVerificationScreen.route)
        }
    }
}

@Composable
fun PhoneTextField(
    phone: MutableState<String>,
    mask: String,
    maskNumber: Char
) {

    TextField(
        value = phone.value,
        onValueChange = { newValue ->
            phone.value = newValue.take(mask.count { it == maskNumber })
        },
        textStyle = MaterialTheme.typography.titleSmall,
        placeholder = {
            Text(
                text = "+7",
                color = systemGrey,
                style = MaterialTheme.typography.titleSmall
            )
        },
        label = {
            Text(
                text = "Phone number",
                style = MaterialTheme.typography.titleSmall,
                color = authViewText
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        keyboardActions = KeyboardActions.Default,
        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
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

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false

        return maskNumber == other.maskNumber
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}