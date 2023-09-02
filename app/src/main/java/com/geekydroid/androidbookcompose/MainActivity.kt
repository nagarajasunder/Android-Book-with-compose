package com.geekydroid.androidbookcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.geekydroid.androidbookcompose.ui.theme.AndroidBookComposeTheme
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current

            AndroidBookComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   Column(modifier = Modifier.fillMaxWidth()) {
                       Button(onClick = {
                           startReceiver(context)
                       }) {
                           Text(text = "Start Listening")
                       }
                   }
                }
            }
        }
    }

    private fun startReceiver(context:Context) {
        val client = SmsRetriever.getClient(context)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            Toast.makeText(context,"started the client",Toast.LENGTH_SHORT).show()
        }
        task.addOnFailureListener {
            Toast.makeText(context,"Failed to start client",Toast.LENGTH_SHORT).show()
        }
    }
}


