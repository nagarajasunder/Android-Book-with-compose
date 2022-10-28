package com.geekydroid.androidbookcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geekydroid.androidbookcompose.ui.theme.AndroidBookComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBookComposeTheme {
                Scaffold {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        AnimateVisibilityDemo()
                    }
                }
            }
        }
    }
}

@Composable
fun AnimateVisibilityDemo() {

    var visible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(onClick = {
            visible = !visible
        }) {
            Text(
                text = if (visible) "Hide" else "Show"
            )
        }

        AnimatedVisibility(visible = visible) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "No Internet connection"
                )
            }
        }
    }
}

//@Preview(showSystemUi = false)
//@Composable
//fun AnimateVisibilityDemoPreview() {
//    AnimateVisibilityDemo()
//}

@Composable
fun AnimateSizeDemo() {

    var size by remember {
        mutableStateOf(30.dp)
    }
    val boxSize by animateDpAsState(
        targetValue = size,
        animationSpec = spring(1000)
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            size+=30.dp
        }) {
            Text(text = "Increase Size")
        }

        Box(
            modifier = Modifier
                .size(boxSize)
                .background(Color.Yellow)
        )
    }

}

@Composable
@Preview(showSystemUi = true)
fun AnimateSizeDemoPreview() {
    AnimateSizeDemo()
}