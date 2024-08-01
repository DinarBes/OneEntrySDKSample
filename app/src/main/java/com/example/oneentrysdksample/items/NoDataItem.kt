package com.example.oneentrysdksample.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun NoDataItem(title: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_related),
            contentDescription = null
        )
        Text(
            text = title,
            color = systemGrey,
            style = MaterialTheme.typography.titleMedium
        )
    }
}