package com.example.oneentrysdksample.items

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.oneentrysdksample.R
import com.example.oneentrysdksample.viewmodel.MainViewModel

@Composable
fun DropDownLocales(
    mainViewModel: MainViewModel
) {

    val locales by mainViewModel.locales.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(id = R.drawable.filter_menu),
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { /*TODO*/ }
        ) {

//            locales?.forEach { locale ->
//                Text(
//                    text = locale.
//                )
//            }
        }
    }
}