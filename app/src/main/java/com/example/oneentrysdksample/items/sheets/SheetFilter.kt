package com.example.oneentrysdksample.items.sheets

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.oneentry.model.common.OneEntryConditionMarker
import com.example.oneentry.model.common.OneEntryFilter
import com.example.oneentrysdksample.Screen
import com.example.oneentrysdksample.ui.theme.backgroundLightGray
import com.example.oneentrysdksample.ui.theme.backgroundProduct
import com.example.oneentrysdksample.ui.theme.lightGrey
import com.example.oneentrysdksample.ui.theme.orange
import com.example.oneentrysdksample.ui.theme.systemGrey
import com.example.oneentrysdksample.viewmodel.CatalogViewModel
import com.example.oneentrysdksample.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlinx.serialization.json.JsonPrimitive

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetFilter(
    modalBottomSheetState: ModalBottomSheetState,
    mainViewModel: MainViewModel,
    catalogViewModel: CatalogViewModel,
    pageUrl: String
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val locales by mainViewModel.locales.collectAsState()
    val attributeColors by catalogViewModel.attributeColors.collectAsState()
    val attributeStickers by catalogViewModel.attributeSticker.collectAsState()

    val bodyFilter: MutableList<OneEntryFilter> = mutableListOf()

    var sliderStartPosition: Float by remember { mutableFloatStateOf(0f) }
    var sliderEndPosition: Float by remember { mutableFloatStateOf(30f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        locales?.let {

            catalogViewModel.getAttributeColor(it.first().code)
            catalogViewModel.getAttributeSticker(it.first().code)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Price, $",
                    color = systemGrey,
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    onClick = {
                        sliderEndPosition = 0f
                        sliderEndPosition = 30f
                    },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = orange
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundLightGray
                    )
                ) {
                    Text(
                        text = "RESET",
                        color = orange,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriceInfo(price = sliderStartPosition) { newValue ->
                    sliderStartPosition = newValue
                    bodyFilter.add(
                        OneEntryFilter(
                            attributeMarker = "price",
                            conditionMarker = OneEntryConditionMarker.MTH,
                            conditionValue = JsonPrimitive(sliderStartPosition),
                            pageUrl = listOf(pageUrl)
                        )
                    )
                }
                PriceInfo(price = sliderEndPosition) { newValue ->
                    sliderEndPosition = newValue
                    bodyFilter.add(
                        OneEntryFilter(
                            attributeMarker = "price",
                            conditionMarker = OneEntryConditionMarker.LTH,
                            conditionValue = JsonPrimitive(sliderEndPosition),
                            pageUrl = listOf(pageUrl)
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "0",
                    color = systemGrey,
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = sliderEndPosition,
                    onValueChange = { sliderEndPosition = it.roundToInt().toFloat() },
                    valueRange = 0f..30f,
                    onValueChangeFinished = {

                    },
                    colors = SliderDefaults.colors(
                        thumbColor = orange,
                        activeTrackColor = orange,
                        inactiveTrackColor = Color.Black.copy(0.2f)
                    )
                )
                Text(
                    text = "30",
                    color = systemGrey,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = "Colors: ",
                color = systemGrey,
                style = MaterialTheme.typography.labelLarge
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Start
            ) {
                attributeColors?.let { colors ->
                    items(colors.listTitles) { item ->

                        val expandedColor = remember { mutableStateOf(false) }

                        ColorView(
                            colorString = item.extended?.asString.toString(),
                            title = item.title,
                            bodyFilter = bodyFilter,
                            pageUrl = pageUrl,
                            expandedColor = expandedColor
                        )
                    }
                }
            }

            Text(
                text = "Stickers: ",
                color = systemGrey,
                style = MaterialTheme.typography.labelLarge
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Start
            ) {
                attributeStickers?.let { stickers ->
                    items(stickers.listTitles) { item ->

                        val expandedSticker = remember { mutableStateOf(false) }

                        StickerView(
                            image = item.extended?.asImage?.downloadLink.toString(),
                            title = item.title,
                            expandedSticker = expandedSticker
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                colors = ButtonDefaults.buttonColors(containerColor = orange),
                onClick = {
                    Log.e("Body filter: ", bodyFilter.toString())
                    catalogViewModel.getProducts(bodyFilter)
                    scope.launch {
                        modalBottomSheetState.hide()
                    }
                }
            ) {
                Text(
                    text = "APPLY",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun PriceInfo(
    price: Float,
    onPriceChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                color = backgroundProduct,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "От",
            color = systemGrey,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Left
        )
        TextField(
            value = String.format("%.0f", price),
            onValueChange = { str ->
                val filteredValue = str.filter { it.isDigit() }.toFloatOrNull() ?: 0f
                if (filteredValue <= 20f) {
                    onPriceChange(filteredValue)
                }
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = systemGrey,
                textColor = systemGrey,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier.width(60.dp)
        )
    }
}

@Composable
fun ColorView(
    colorString: String,
    title: String,
    bodyFilter: MutableList<OneEntryFilter>,
    pageUrl: String,
    expandedColor: MutableState<Boolean>
) {

    val color = Color(android.graphics.Color.parseColor(colorString)).copy(alpha = 1.0f)

    Row(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                if (expandedColor.value) {
                    bodyFilter.removeIf { filter ->
                        filter.attributeMarker == "color" &&
                                filter.conditionMarker == OneEntryConditionMarker.IN &&
                                filter.conditionValue == JsonPrimitive(title) &&
                                filter.pageUrl.contains(pageUrl)
                    }
                    expandedColor.value = false
                } else {
                    bodyFilter.add(
                        OneEntryFilter(
                            attributeMarker = "color",
                            conditionMarker = OneEntryConditionMarker.IN,
                            conditionValue = JsonPrimitive(title),
                            pageUrl = listOf(pageUrl)
                        )
                    )
                    expandedColor.value = true
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Canvas(modifier = Modifier
            .size(26.dp)
            .border(
                width = 2.dp,
                color = if (expandedColor.value) orange else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
        ) {
            drawCircle(color = color)
        }
        Text(
            text = title,
            color = if (expandedColor.value) orange else lightGrey,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun StickerView(
    image: String,
    title: String,
    expandedSticker: MutableState<Boolean>
) {

    Row(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                expandedSticker.value = !expandedSticker.value
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = image,
            contentDescription = null
        )
        Text(
            text = title,
            color = if (expandedSticker.value) orange else systemGrey,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}