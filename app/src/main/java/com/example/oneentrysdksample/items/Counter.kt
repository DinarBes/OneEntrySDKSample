package com.example.oneentrysdksample.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.ui.theme.backgroundProduct
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun Counter(
    countProduct: MutableState<Int>
) {

    Row(
        modifier = Modifier
            .background(
                color = backgroundProduct,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(90.dp)
                )
                .size(15.dp)
                .clickable(
                    onClick = {
                        if (countProduct.value > 0) {

                            countProduct.value -= 1
                        }
                    }
                ),
            painter = painterResource(id = R.drawable.minus),
            contentDescription = "minus",
            tint = systemGrey
        )

        Text(
            text = countProduct.value.toString(),
            fontWeight = FontWeight.W500,
            fontSize = 20.sp,
            color = Color.Black
        )

        Icon(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(90.dp)
                )
                .size(15.dp)
                .clickable(
                    onClick = {
                        countProduct.value += 1
                    }
                ),
            imageVector = Icons.Default.Add,
            contentDescription = "add",
            tint = systemGrey
        )
    }
}