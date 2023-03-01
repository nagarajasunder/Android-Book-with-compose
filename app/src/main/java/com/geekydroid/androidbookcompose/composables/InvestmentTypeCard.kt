package com.geekydroid.androidbookcompose.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekydroid.androidbookcompose.R

@Composable
fun InvestmentTypeCard(modifier: Modifier = Modifier) {

    var expanded by mutableStateOf(false)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "Indian Stocks",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            if (expanded) {
                InvestmentDateExpanded()
            } else {
                InvestmentDataShrunked()
            }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                    },
                painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                contentDescription = null,
                alignment = Alignment.Center
            )

        }
    }
}

@Composable
fun InvestmentDataShrunked() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Buy",
                style = TextStyle(fontSize = 18.sp)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                text = "$15.89k",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,

                    )
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Sell",
                style = TextStyle(fontSize = 18.sp)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                text = "$1234.89k",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.End
                )
            )

        }


    }
}

@Composable
fun InvestmentDateExpanded(modifier:Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Buy",
                style = TextStyle(fontSize = 18.sp)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                text = "$15,890.76k",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,

                    )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "Sell",
                style = TextStyle(fontSize = 18.sp)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                text = "$62,098.34",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,

                    )
            )
        }


    }
}

@Preview
@Composable
fun InvestmentTypeCardPreview() {
    InvestmentTypeCard()
}