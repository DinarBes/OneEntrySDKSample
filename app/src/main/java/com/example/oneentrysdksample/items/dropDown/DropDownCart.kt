package com.example.oneentrysdksample.items.dropDown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.unit.dp
import com.example.oneentrysdksample.ui.theme.backgroundProduct
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey

@Composable
fun DropDownCart() {

    var expanded by remember { mutableStateOf(false) }
    val expandedDelete = remember { mutableStateOf(false) }
    val expandedAddToList = remember { mutableStateOf(false) }
    val expandedShare = remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = systemGrey,
                modifier = Modifier.size(20.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    color = backgroundProduct,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        expandedDelete.value = !expandedDelete.value
                    },
                text = "Delete",
                color = if (expandedDelete.value) orange else systemGrey,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .clickable {
                        expandedAddToList.value = !expandedAddToList.value
                    },
                text = "Add to List",
                color = if (expandedAddToList.value) orange else systemGrey,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .clickable {
                        expandedShare.value = !expandedShare.value
                    },
                text = "Share",
                color = if (expandedShare.value) orange else systemGrey,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}