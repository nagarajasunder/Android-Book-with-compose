package com.geekydroid.androidbookcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.geekydroid.androidbookcompose.ui.theme.AndroidBookComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val data by viewModel.uiData.observeAsState(initial = ScreenData.initialState)
            AndroidBookComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScreenContent(
                        data,
                        onSuccessClick = { viewModel.postSuccess() },
                        onErrorClick = { viewModel.postError() }
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenContent(
    data: ScreenData,
    onSuccessClick: () -> Unit,
    onErrorClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onSuccessClick) {
            Text(text = "Update Success")
        }

        Button(onClick = onErrorClick) {
            Text(text = "Update Failure")
        }
       LazyRow() {
           items(data.dataList.size) { index ->
               Text(text = data.dataList[index])

           }
       }
        when (data.data) {
            is UiResult.Error -> {
                Text(text = "Error")
            }
            is UiResult.Loading -> {
                Text(text = "Loading")
            }
            is UiResult.Success -> {
                Text(text = "Success")
            }
        }
    }
}

sealed class UiResult<T>(
    val data: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null
) {

    data class Loading<T>(val messageText: String? = null) : UiResult<T>(message = messageText)
    data class Success<T>(val successData: T) : UiResult<T>(data = successData)
    data class Error<T>(val error: Throwable? = null) : UiResult<T>(throwable = error)

}