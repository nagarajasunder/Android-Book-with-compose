package com.geekydroid.androidbookcompose

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ResistanceConfig
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private const val TAG = "SwipeToStart"


@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToStartTrip(
    modifier: Modifier = Modifier
) {

    val swipeBoxSize = 60.dp
    val configuration = LocalConfiguration.current
    val swipeWidth =  configuration.screenWidthDp.dp - swipeBoxSize
    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { swipeWidth.toPx() }
    val anchors = mapOf(
        0f to 0,
        sizePx to 1
    )
    val context = LocalContext.current
    LaunchedEffect(key1 = swipeableState.currentValue) {
        if (swipeableState.currentValue == 1) {
            Toast.makeText(context,"Swipe Completed",Toast.LENGTH_SHORT).show()
        }
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(swipeBoxSize)
            .background(Color(0XFF00c25a))
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                resistance = ResistanceConfig(30f,30f,40f)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(painter = painterResource(id = R.drawable.green_start_trip), contentDescription = null)
        Box(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(swipeBoxSize)
                .background(Color(0XFF158949)),
            contentAlignment = Alignment.Center,
        ) {
            Image(painter = painterResource(id = R.drawable.lynk_window), contentDescription = null)
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Swipe to Start",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SwipeToStartTripPreview() {
    Row {
        SwipeToStartTrip(modifier = Modifier)
//        Button(onClick = { /*TODO*/ }) {
//            Text(text = "fsad")
//        }
    }
}