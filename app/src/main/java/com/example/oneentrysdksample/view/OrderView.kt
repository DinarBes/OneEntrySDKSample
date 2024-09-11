package com.example.oneentrysdksample.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneentrysdksample.items.DeliveryItem
import com.example.oneentrysdksample.items.OrderProductItem
import com.example.oneentrysdksample.items.calendar_time.CalendarDataSource
import com.example.oneentrysdksample.items.calendar_time.DatePickerDialog
import com.example.oneentrysdksample.items.calendar_time.TimePickerDialog
import com.example.oneentrysdksample.ui.theme.Purple80
import com.example.oneentrysdksample.ui.theme.backgroundLightGray
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.view.auth.FormView
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderView(
    catalogViewModel: CatalogViewModel,
    mainViewModel: MainViewModel
) {

    val context = LocalContext.current
    val products by catalogViewModel.cartProducts.collectAsState()
    val delivery by mainViewModel.delivery.collectAsState()

    var showDialogDate by remember { mutableStateOf(false) }
    val dataSource = CalendarDataSource()
    val calendarUiModel = remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }

    var showDialogTime by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = calendarUiModel.value.selectedDate.date.atStartOfDay().hour,
        initialMinute = calendarUiModel.value.selectedDate.date.atStartOfDay().minute
    )

    val locale by mainViewModel.locales.collectAsState()

    val address = remember { mutableStateOf("") }

    val finalProductPrice = remember {
        products?.map {
            it.price?.let { it1 ->
                mutableDoubleStateOf(it1)
            }
        }
    }
    val totalPrice = remember { mutableDoubleStateOf(0.0) }

    totalPrice.doubleValue = finalProductPrice?.filterNotNull()?.sumOf { it.doubleValue } ?: 0.0

    val enabled by remember { derivedStateOf { totalPrice.doubleValue > 0.0 } }

    locale?.first()?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            products?.forEach { product ->
                finalProductPrice?.mapNotNull { it1 ->
                    if (it1 != null) {
                        OrderProductItem(
                            mainViewModel = mainViewModel,
                            product = product,
                            productPrice = it1,
                            locale = it
                        )
                    }
                }
            }
            delivery?.let { it1 ->
                DeliveryItem(
                    product = it1,
                    locale = it
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDialogDate = !showDialogDate
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = calendarUiModel.value.selectedDate.date.toString(),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W400),
                        color = systemGrey
                    )

                    IconButton(
                        onClick = {
                            showDialogDate = !showDialogDate
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = orange
                        )
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.Gray.copy(0.8f)
                )
            }
            if (showDialogDate) {
                DatePickerDialog(
                    onDismissRequest = { showDialogDate = false },
                    dataSource = dataSource,
                    calendarUiModel = calendarUiModel
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDialogTime = !showDialogTime
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${timePickerState.hour}:${timePickerState.minute}",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W400),
                        color = systemGrey
                    )

                    IconButton(
                        onClick = {
                            showDialogTime = !showDialogTime
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null,
                            tint = orange
                        )
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.Gray.copy(0.8f)
                )
            }
            if (showDialogTime) {
                TimePickerDialog(
                    onDismissRequest = { /*TODO*/ },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialogTime = !showDialogTime
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialogTime = !showDialogTime
                            }
                        ) { Text("Cancel") }
                    }
                )
                {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            clockDialColor = Color.White,
                            clockDialUnselectedContentColor = orange,
                            selectorColor = lightGrey,
                            containerColor = orange,
                            periodSelectorSelectedContainerColor = systemGrey,
                            periodSelectorUnselectedContainerColor = backgroundLightGray,
                            periodSelectorSelectedContentColor = Color.White,
                            timeSelectorSelectedContainerColor = orange.copy(0.5f)
                        )
                    )
                }
            }
            FormView(
                nameField = "Address",
                text = address,
                icon = null
            ) {

            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total amount:",
                    color = systemGrey,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "$ ${totalPrice.doubleValue + delivery?.price!!}",
                    color = systemGrey,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = if (enabled) orange else orange.copy(0.7f)),
                enabled = enabled,
                onClick = {
                    Toast.makeText(context, "Go to Pay", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(
                    text = "GO TO PAY",
                    color = Color.White,
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp
                )
            }
        }
    }
}