package com.geekydroid.androidbookcompose.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.google.gson.annotations.SerializedName

data class Line(
    @SerializedName("start_x")
    val startX: Float,
    @SerializedName("start_y")
    val startY: Float,
    @SerializedName("end_x")
    val endX: Float,
    @SerializedName("end_y")
    val endY: Float
)

@Composable
fun ComposeCanvasRead(
    modifier: Modifier = Modifier,
    lines:List<Line>
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        lines.forEach {line ->
            drawLine(color = Color.Gray, start = Offset(line.startX,line.startY), end = Offset(line.endX,line.endY), strokeWidth = 12f)
        }
    }
}

@Composable
fun ComposeCanvas(
    modifier: Modifier = Modifier,
    onDraw: (Line) -> Unit
) {
    val lines = remember {
        mutableStateListOf<Line>()
    }
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val startPosition = change.position - dragAmount
                    val newLine = Line(
                        startX = startPosition.x,
                        startY = startPosition.y,
                        endX = change.position.x,
                        endY = change.position.y
                    )
                    lines.add(
                        newLine
                    )
                    onDraw(newLine)
                }
            }
    ) {
        lines.forEach {line ->
            drawLine(color = Color.Gray, start = Offset(line.startX,line.startY), end = Offset(line.endX,line.endY), strokeWidth = 12f)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeCanvasPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        ComposeCanvas() {

        }
    }
}