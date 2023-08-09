package com.geekydroid.androidbookcompose.composables

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun MyBasicRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->

        //Measure its children
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        //Decide its own size
        layout(constraints.maxWidth, constraints.maxHeight) {
            var xPosition = 0
            var yPosition = 0
            var maxYPosition = 0
            placeables.forEach { placeable ->
                if ((xPosition + placeable.width) > constraints.maxWidth) {
                    xPosition = 0
                    yPosition += maxYPosition
                    maxYPosition = 0
                }
                if (placeable.height > maxYPosition) {
                    maxYPosition = placeable.height
                }

                placeable.placeRelative(xPosition, yPosition)
                xPosition += placeable.width
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun Sample() {
    BottomNavigation() {
        Row {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            ) {
                MyBottomNavItem(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth(),
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = null)
                    },
                    text = {
                        Text(text = "Home")
                    },
                    animationProgress = 0.2f
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            ) {
                MyBottomNavItem(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth(),
                    icon = {
                        Icon(Icons.Default.ThumbUp, contentDescription = null)
                    },
                    text = {
                        Text(text = "Like")
                    },
                    animationProgress = 0.2f
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            ) {
                MyBottomNavItem(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth(),
                    icon = {
                        Icon(Icons.Default.ThumbDown, contentDescription = null)
                    },
                    text = {
                        Text(text = "Unlike")
                    },
                    animationProgress = 0.2f
                )
            }
        }
    }

}

@Composable
fun MyBottomNavItem(
    modifier: Modifier = Modifier,
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(0.0, 1.0) animationProgress: Float
) {

    Layout(content = {
        Box(modifier = modifier.layoutId("icon")) {
            icon()
        }
        Box(modifier = modifier.layoutId("text")) {
            text()
        }
    }) { measurables, constraints ->

        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)

        val iconY = (constraints.maxHeight - iconPlaceable.height) / 2
        val textY = (constraints.maxHeight - textPlaceable.height) / 2
        val textWidth = textPlaceable.width * animationProgress
        val iconX = (constraints.maxWidth - textWidth - iconPlaceable.width) / 2
        val textX = iconX + iconPlaceable.width
        layout(constraints.maxWidth, constraints.maxHeight) {
            iconPlaceable.placeRelative(iconX.toInt(), iconY)
            if (animationProgress != 0f) {
                textPlaceable.placeRelative(textX.toInt(), textY)
            }
        }
    }

}