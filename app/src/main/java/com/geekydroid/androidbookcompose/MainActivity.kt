package com.geekydroid.androidbookcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    val viewmodel:MainViewmodel by viewModels()
                    val screenState by viewmodel.screenState.observeAsState(ScreenState.initialState)
                    ScreenContent(screenState,viewmodel)
                }
            }
        }
    }
}

@Composable
fun ScreenContent(state:ScreenState,actions:ScreenActions)
{
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = state.value, onValueChange = {
            actions.onTextValueChange(it)
        })
        Button(onClick = { actions.onSubmitButtonClicked() }) {
            Text("Encrypt")
        }
        Button(onClick = { actions.onDecryptButtonClicked() }) {
            Text("Decrypt")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidBookComposeTheme {
        Greeting("Android")
    }
}