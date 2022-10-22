package com.geekydroid.androidbookcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
            AndroidBookComposeTheme {
                // A surface container using the 'background' color from the theme

                val viewmodel:MainViewmodel = hiltViewModel()
                val importedData by viewmodel.importedData.observeAsState("")
                val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()){ result ->
                    if (result.resultCode == RESULT_OK && result.data != null)
                    {
                        result.data!!.data?.let {
                            viewmodel.readCsv(it)
                        }
                    }
                }
                LaunchedEffect(key1 = Unit)
                {
                    viewmodel.events.collect{ events ->
                        when(events)
                        {
                            ScreenEvents.OpenFilePicker -> {
                                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                                intent.type = "*/*"
                                launcher.launch(intent)

                            }
                        }
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SafDemo(
                        actions = viewmodel,
                        data = importedData
                    )
                }
            }
        }
    }
}

@Composable
fun SafDemo(
    modifier: Modifier = Modifier,
    actions: ScreenActions,
    data:String
)
{
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = actions::onOpenSafClicked) {
            Text(text = "Storage Access Framework Demo")
        }
        Button(onClick = actions::accessDataFromSavedUri) {
            Text(text = "Access data from saved URI")
        }
        Text(text = data)
    }
}