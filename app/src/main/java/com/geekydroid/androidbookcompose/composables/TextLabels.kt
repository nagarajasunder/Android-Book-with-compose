package com.geekydroid.androidbookcompose.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextLabels() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
    ) {
        val textMeasurer = rememberTextMeasurer()

        Canvas(modifier = Modifier.fillMaxSize()){

            var textX = 0f
            var textY = 0f
            repeat(5) {
                val textMeasureResult = textMeasurer.measure("$it Hello")
                drawText(textMeasurer,"$it Hello", topLeft = Offset(textX,textY))
                textY+=(textMeasureResult.lineCount*(size.height/4))
            }
        }
    }

}

@Preview
@Composable
fun TextLabelsPreview() {
    TextLabels()
}