package com.geekydroid.androidbookcompose

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.geekydroid.androidbookcompose.ui.theme.AndroidBookComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
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
                    val viewmodel: MainViewmodel by viewModels()
                    val screenState by viewmodel.screenState.observeAsState(screenState.initialState)
                    ScreenContent(actions = viewmodel, screenState = screenState)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScreenContent(actions: screenActions, screenState: screenState) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var count by remember{
            mutableStateOf(0)
        }
        if (screenState.launchPermissionRequest)
        {
            CheckAndShowLocationPermissions(object : PermissionResultCallback{
                override fun permissionGranted() {
                    Toast.makeText(context,"Permission Granted",Toast.LENGTH_SHORT).show()
                    actions.allPermissionsGranted()
                }

                override fun permissionDenied() {
                    Toast.makeText(context,"Permission Denied ${count++}",Toast.LENGTH_SHORT).show()
                    actions.shouldShowRationaled()
                }

            },context)
        }
        if (screenState.showPermissionConcernDialog) {
            AlertDialog(
                onDismissRequest = { actions.permissionConcernDialogDismissed() },
                title = {
                    Text(text = "We need your location permission")
                },
                text = {
                    Text(text = "We want the location permission for proper functioning of the app")
                },
                confirmButton = {
                    Button(onClick = { actions.permissionConcernDialogDismissed() }) {
                        Text(text = "Ok")
                    }
                })
        }

        Button(onClick = {
            actions.requestPermissionButtonClicked()
        }) {
            Text(text = "Request Location Permission")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidBookComposeTheme {

    }
}