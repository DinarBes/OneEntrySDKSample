package com.example.oneentrysdksample.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.items.MenuItem
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle,
    onItemClick: (MenuItem) -> Unit
) {

    Spacer(modifier = Modifier.height(30.dp))
    Image(
        modifier = Modifier.padding(20.dp),
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null
    )
    LazyColumn(
        modifier = modifier
    ) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = item.idIcon),
                    contentDescription = null,
                    tint = systemGrey
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    modifier = Modifier.weight(1f),
                    style = itemTextStyle
                )
            }
        }
    }
}