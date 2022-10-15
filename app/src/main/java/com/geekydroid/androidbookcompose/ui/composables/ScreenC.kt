package com.geekydroid.androidbookcompose.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.geekydroid.androidbookcompose.ui.screenspec.Screen
import com.geekydroid.androidbookcompose.ui.screenspec.buttonClickCallback

@Composable
fun ScreenCContent(callback: buttonClickCallback) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Screen C", fontSize = 40.sp)
        Button(onClick = {
            callback.onButtonClick(Screen.SCREEN_A)
        }) {
            Text(text = "Screen A")
        }
        Button(onClick = {
            callback.onButtonClick(Screen.SCREEN_B)
        }) {
            Text(text = "Screen B")
        }
        Button(onClick = {
            callback.onButtonClick(Screen.SCREEN_C)
        }) {
            Text(text = "Screen C")
        }
    }
}