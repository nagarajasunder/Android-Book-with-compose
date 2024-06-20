package com.geekydroid.androidbookcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geekydroid.androidbookcompose.composables.ComposeCanvasRead
import com.geekydroid.androidbookcompose.ui.theme.AndroidBookComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBookComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel:MainViewModel = hiltViewModel()
                    val screenData by viewModel.screenState.collectAsState()
                    Column {
                        ComposeCanvasRead(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(500.dp),
                            lines = screenData.lines
                        )
                        Button(onClick = {viewModel.connectToWebSocket()}) {
                            Text(text = "Connect")
                        }
                    }
                }
            }
        }
    }
}
