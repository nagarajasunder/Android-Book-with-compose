package com.geekydroid.androidbookcompose

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FrequencyAnimation() {

    Row(
    ) {
        FrequencyBar(
            targetDp = 170.dp,
            animationSpec = keyframes {
                durationMillis = 3000
                75.dp at 500 with LinearEasing
                50.dp at 1000 with FastOutSlowInEasing
                80.dp at 1500 with LinearEasing
                25.dp at 2000 with FastOutSlowInEasing
                170.dp at 2500 with LinearOutSlowInEasing
            }
        )
        FrequencyBar(
            targetDp = 75.dp,
            animationSpec = keyframes {
                durationMillis = 3000
                200.dp at 500 with LinearEasing
                25.dp at 1000 with LinearEasing
                80.dp at 1500 with LinearEasing
                50.dp at 2000 with LinearEasing
                75.dp at 2500 with LinearEasing
            }
        )
        FrequencyBar(
            targetDp = 150.dp,
            animationSpec = keyframes {
                durationMillis = 3000
                80.dp at 500 with LinearEasing
                50.dp at 1000 with LinearEasing
                125.dp at 1500 with LinearEasing
                70.dp at 2000 with LinearEasing
                150.dp at 2500 with LinearEasing
            }
        )
        FrequencyBar(
            targetDp = 180.dp,
            animationSpec = keyframes {
                durationMillis = 3000
                80.dp at 500 with LinearEasing
                25.dp at 1000 with LinearEasing
                130.dp at 1500 with LinearEasing
                70.dp at 2000 with LinearEasing
                180.dp at 2500 with LinearEasing
            }
        )
        FrequencyBar(
            targetDp = 120.dp,
            animationSpec = keyframes {
                durationMillis = 3000
                80.dp at 500 with LinearEasing
                50.dp at 1000 with LinearEasing
                125.dp at 1500 with LinearEasing
                60.dp at 2000 with LinearEasing
                120.dp at 2500 with LinearEasing
            }
        )
    }
}

@Composable
fun FrequencyBar(
    initialDp: Dp = 0.dp,
    targetDp: Dp,
    animationSpec: AnimationSpec<Dp>
) {

    var height by remember {
        mutableStateOf(initialDp)
    }

    val heightAnimation by animateDpAsState(targetValue = height, animationSpec)
    LaunchedEffect(key1 = Unit) {
        height = targetDp
    }

    Box(
        modifier = Modifier
            .width(30.dp)
            .padding(horizontal = 2.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Card(
            modifier = Modifier
                .width(30.dp)
                .height(heightAnimation),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color(0xff1DB954)
        ) {

        }
    }

}

@Composable
@Preview(showSystemUi = true)
fun FrequencyAnimationPreview() {
    FrequencyAnimation()
}