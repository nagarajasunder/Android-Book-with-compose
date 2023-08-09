package com.geekydroid.androidbookcompose.composables

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil


private const val TAG = "LineGraph"

@Composable
fun LineGraph(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        val months =
            listOf(
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec"
            )
        val expenseByMonth =
            listOf(12000, 5500, 14500, 30000, 24000, 19000, 10500, 13000, 15000, 12000, 12500, 6500)
        val minValue = expenseByMonth.min()
        val maxValue = expenseByMonth.max()
        val graphItemList = getGraphLabelsList(minValue, maxValue, expenseByMonth.size)
        val diffBtwEachLabel = graphItemList[1] - graphItemList[0]
        val linesBtwLabels = 10
        Log.d(TAG, "LineGraph: labels $graphItemList")

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.White)
        ) {
            val textMeasurer: TextMeasurer = rememberTextMeasurer()
            val horizontalTextMeasureResult = textMeasurer.measure(months[0])
            val verticalTextMeasureResult = textMeasurer.measure(graphItemList[0].toString())
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(
                    color = Color.White,
                    style = Stroke(2.dp.toPx())
                )

                val graphWidth = size.width
                val graphHeight = size.height - horizontalTextMeasureResult.size.height
                val verticalLines = expenseByMonth.size
                val verticalSize = graphWidth / (verticalLines + 1)
                val horizontalLines = expenseByMonth.size
                val horizontalSize = graphHeight / (horizontalLines + 1)
                val horizontalSizePerLine = horizontalSize/linesBtwLabels
                repeat(verticalLines) { index ->
                    val x = verticalSize * (index + 1)
                    drawLine(
                        color = Color.Black,
                        start = Offset(x, 0f),
                        end = Offset(x, graphHeight),
                        strokeWidth = 1f
                    )
                }
                val horizontalLineX = verticalTextMeasureResult.size.width + 10f
                repeat(horizontalLines) { index ->
                    val y = horizontalSize * (index + 1)
                    drawLine(
                        color = Color.Black,
                        start = Offset(horizontalLineX, y),
                        end = Offset(graphWidth, y),
                        strokeWidth = 1f
                    )
                }
                //Horizontal Labels
                var textX = verticalSize / 2
                months.forEachIndexed { index, month ->
                    drawText(
                        textMeasurer = textMeasurer,
                        text = month,
                        topLeft = Offset(textX, graphHeight),
                        style = TextStyle.Default.copy(
                            textAlign = TextAlign.Center, letterSpacing = TextUnit(
                                0.5f,
                                TextUnitType.Sp
                            )
                        )
                    )
                    textX = ((index + 1) * verticalSize) + verticalSize / 2

                }
                //Vertical Labels
                var textY = horizontalSize - 20f
                graphItemList.reversed().forEachIndexed { index, graphLabel ->
                    drawText(
                        textMeasurer = textMeasurer,
                        text = graphLabel.toString(),
                        topLeft = Offset(0f,textY),
                        style = TextStyle.Default.copy(fontSize = 12.sp, textAlign = TextAlign.Start)
                    )
                    textY+=horizontalSize
                }

                val path = Path()
                var x = 0f
                var y = graphHeight
                path.moveTo(x, y)
                expenseByMonth.forEach { dataPoint ->
                    val firstGreaterElement = graphItemList.first { it >= dataPoint }
                    val firstGreaterElementIndex = graphItemList.indexOf(firstGreaterElement)
                    val firstSmallerElement = if (firstGreaterElementIndex == 0) {
                        graphItemList[firstGreaterElementIndex]
                    }
                    else {
                        graphItemList[firstGreaterElementIndex-1]
                    }
                    val firstSmallerElementIndex = graphItemList.indexOf(firstSmallerElement)
                    Log.d(TAG, "LineGraph: data point $dataPoint firstSmallEle $firstSmallerElement indx $firstSmallerElementIndex")
                    x += verticalSize
//                    y = graphHeight - ((firstSmallerElementIndex * horizontalSize) + (((dataPoint - firstSmallerElement) / diffBtwEachLabel) * (horizontalSize)))
                   val rem = (ceil(((dataPoint - firstSmallerElement)/diffBtwEachLabel).toDouble()).toFloat())*horizontalSizePerLine
                    y = graphHeight - (((firstSmallerElementIndex+1)*horizontalSize) + rem)

                    path.lineTo(x, y)
                    drawCircle(color = Color.Red, radius = 10f, center = Offset(x, y))
                }
                drawPath(path, color = Color.Blue, style = Stroke(2.dp.toPx()))
            }
        }
    }
}

fun getGraphLabelsList(minValue: Int, maxValue: Int, labelsNeeded: Int): List<Int> {
    /**
     * Min is 5000
     */
    val labelsList = mutableListOf<Int>()
    repeat(labelsNeeded) { index ->
        val label = minValue + (index.toFloat() / (labelsNeeded - 1)) * (maxValue - minValue)
        labelsList.add(label.toInt())
    }
    return labelsList
}


@Preview
@Composable
fun LineGraphPreview() {
    LineGraph()
}