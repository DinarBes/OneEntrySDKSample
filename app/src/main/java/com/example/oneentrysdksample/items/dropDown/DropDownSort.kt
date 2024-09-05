package com.example.oneentrysdksample.items.dropDown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.oneentry.model.common.OneEntrySortDirection
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel

@Composable
fun SortedItem(
    catalogViewModel: CatalogViewModel
) {

    var expanded by remember { mutableStateOf(false) }
    var expandedLowHigh by remember { mutableStateOf(false) }
    var expandedHighLow by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(id = R.drawable.filter_menu),
                contentDescription = null,
                tint = systemGrey,
                modifier = Modifier.size(20.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        expandedLowHigh = true
                        expandedHighLow = false
                        catalogViewModel.getProducts(
                            body = listOf(),
                            sortKey = "price",
                            sortOrder = OneEntrySortDirection.ASK
                        )
                    },
                text = "Price: Low to High",
                color = if (expandedLowHigh) orange else systemGrey,
                style = MaterialTheme.typography.titleMedium
            )
            Divider(
                thickness = 2.dp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                modifier = Modifier
                    .clickable {
                        expandedHighLow = true
                        expandedLowHigh = false
                        catalogViewModel.getProducts(
                            body = listOf(),
                            sortKey = "price",
                            sortOrder = OneEntrySortDirection.DESK
                        )
                    },
                text = "Price: High to Low",
                color = if (expandedHighLow) orange else systemGrey,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}