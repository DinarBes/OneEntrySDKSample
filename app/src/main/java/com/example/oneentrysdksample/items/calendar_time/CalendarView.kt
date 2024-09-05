package com.example.oneentrysdksample.items.calendar_time

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    dataSource: CalendarDataSource,
    calendarUiModel: MutableState<CalendarUiModel>
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 15.dp,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                DatePicker(
                    dataSource = dataSource,
                    calendarUiModel = calendarUiModel
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = orange,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = "Apply",
                        color = orange,
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DatePicker(
    dataSource: CalendarDataSource,
    calendarUiModel: MutableState<CalendarUiModel>
) {

    Header(
        data = calendarUiModel.value,
        onPrevClickListener = { startDate ->
            val finalStartDate = startDate.minusDays(1)
            calendarUiModel.value = dataSource.getData(
                startDate = finalStartDate,
                lastSelectedDate = calendarUiModel.value.selectedDate.date
            )
        },
        onNextClickListener = { endDate ->
            val finalStartDate = endDate.plusDays(2)
            calendarUiModel.value = dataSource.getData(
                startDate = finalStartDate,
                lastSelectedDate = calendarUiModel.value.selectedDate.date
            )
        }
    )
    Content(
        data = calendarUiModel.value,
        onDateClickListener = { date ->
            calendarUiModel.value = calendarUiModel.value.copy(
                selectedDate = date,
                visibleDates = calendarUiModel.value.visibleDates.map {
                    it.copy(
                        isSelected = it.date.isEqual(date.date)
                    )
                }
            )
        }
    )
}

@Composable
fun Header(
    data: CalendarUiModel,
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onPrevClickListener(data.startDate.date)
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous",
                tint = lightGrey
            )
        }
        Text(
            text = if (data.selectedDate.isToday) {
                "Today"
            } else {
                data.selectedDate.date.format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                )
            },
            color = orange,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(
            onClick = {
                onNextClickListener(data.endDate.date)
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = lightGrey
            )
        }
    }
}

@Composable
fun ContentItem(
    date: CalendarUiModel.Date,
    onDateClickListener: (CalendarUiModel.Date) -> Unit
) {

    val today = LocalDate.now()
    val isDateBeforeToday = date.date.isBefore(today)

    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .then(
                if (!isDateBeforeToday) {
                    Modifier.clickable {
                        onDateClickListener(date)
                    }
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (date.isSelected) {
                orange
            } else {
                Color.White
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            Text(
                text = date.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall,
                color = if (date.isSelected) {
                    Color.White
                } else {
                    if (isDateBeforeToday) {
                        Color.Gray
                    } else {
                        orange
                    }
                }
            )
            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
                color = if (date.isSelected) {
                    Color.White
                } else {
                    if (isDateBeforeToday) {
                        Color.Gray
                    } else {
                        systemGrey
                    }
                }
            )
        }
    }
}


@Composable
fun Content(
    data: CalendarUiModel,
    onDateClickListener: (CalendarUiModel.Date) -> Unit
) {
    LazyRow {
        items(items = data.visibleDates) { date ->
            ContentItem(
                date = date,
                onDateClickListener = onDateClickListener
            )
        }
    }
}

