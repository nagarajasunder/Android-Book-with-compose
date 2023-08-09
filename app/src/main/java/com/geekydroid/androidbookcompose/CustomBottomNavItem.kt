package com.geekydroid.androidbookcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RotateLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomBottomNavItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    isSelected: Boolean
) {
    Layout(
        modifier = Modifier.fillMaxWidth(),
        content = {
            Box(modifier = Modifier.layoutId("icon"), content = icon)
            Box(modifier = Modifier.layoutId("text"), content = text)
        }
    ) { measurables, constraints ->


        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)

        val iconY = (constraints.maxHeight) / 2
        val textY = (constraints.maxHeight) / 2

        val textWidth = textPlaceable.width
        val iconX = (constraints.maxWidth - textWidth - iconPlaceable.width) / 2f
        val textX = (iconX + iconPlaceable.width)

        layout(constraints.maxWidth, constraints.maxHeight) {
            iconPlaceable.placeRelative(iconX.toInt(), iconY)
            if (isSelected) {
                textPlaceable.placeRelative(textX.toInt(), textY)
            }
        }
    }
}

@Preview
@Composable
fun BottomNavItemPreview() {

    val iconsList = listOf(
        Icons.Filled.Search,
        Icons.Filled.Phone,
        Icons.Filled.MusicNote,
        Icons.Filled.RotateLeft
    )

    val textList = listOf(
        "Search",
        "Phone",
        "Music",
        "Rotate"
    )

    Row {
        CustomBottomNavItem(
            icon = { Icon(iconsList[0], contentDescription = textList[0]) },
            text = {
                Text(
                    text = textList[0]
                )
            },
            isSelected = true
        )
        CustomBottomNavItem(
            icon = { Icon(iconsList[1], contentDescription = textList[1]) },
            text = {
                Text(
                    text = textList[1]
                )
            },
            isSelected = true
        )
    }
}