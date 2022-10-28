package com.geekydroid.androidbookcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
                        AnimateContentSizeDemo()
                        AnimateDpDemo()
                        CrossfadeDemo()
                        UpdateTransitionDemo()
                        KeyFrameAnimationSpecDemo()
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
fun AnimateDpDemo() {

    var size by remember {
        mutableStateOf(30.dp)
    }
    val boxSize by animateDpAsState(
        targetValue = size,
        animationSpec = spring(
            0.5f,
            stiffness = Spring.StiffnessLow
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            size += 30.dp
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
fun AnimateDpDemoPreview() {
    AnimateDpDemo()
}

@Composable
fun AnimateContentSizeDemo() {
    var message by remember { mutableStateOf("Hello") }
    var size by remember {
        mutableStateOf(45.dp)
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .animateContentSize()

        ) {
            Text(
                text = message,
                Modifier.padding(16.dp)
            )
        }
        Button(onClick = {
            message = "Updated Content"
        }) {
            Text(text = "Change Content")
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun AnimateContentSizeDemoPreview() {
    AnimateContentSizeDemo()
}

@Composable
fun CrossfadeDemo() {
    var bool by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Crossfade(
            targetState = bool,
            animationSpec = spring(0.5f, stiffness = 3f)
        ) {
            if (it) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Blue)
                ) {
                    Text(
                        text = "Page A",
                        Modifier.padding(8.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red)
                ) {
                    Text(
                        text = "Page B",
                        Modifier.padding(8.dp)
                    )
                }
            }
        }
        Button(onClick = {
            bool = !bool
        }) {
            Text(text = "Change Content")
        }
    }
}

@Composable
fun UpdateTransitionDemo() {

    var currentState by remember {
        mutableStateOf(BoxState.COLLAPSED)
    }
    val transition = updateTransition(targetState = currentState, label = "")
    val color by transition.animateColor(
        label = "",
        transitionSpec = {
            when {
                BoxState.COLLAPSED isTransitioningTo BoxState.EXPANDED ->
                    spring(0.5f, stiffness = 40f)
                BoxState.EXPANDED isTransitioningTo BoxState.COLLAPSED ->
                    tween(500)
                else -> tween(500)
            }
        }) { state ->
        when (state) {
            BoxState.COLLAPSED -> Color.Yellow
            BoxState.EXPANDED -> Color.Blue
        }
    }
    val size by transition.animateDp(label = "") { state ->
        when (state) {
            BoxState.COLLAPSED -> 40.dp
            BoxState.EXPANDED -> 80.dp
        }
    }

    val borderColor by transition.animateColor(label = "") { state ->
        when (state) {
            BoxState.COLLAPSED -> Color.Blue
            BoxState.EXPANDED -> Color.Yellow
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                currentState = BoxState.COLLAPSED
            }) {
                Text(text = "Collapse")
            }
            Button(onClick = {
                currentState = BoxState.EXPANDED
            }) {
                Text(text = "Expand")
            }

        }
        Box(
            modifier = Modifier
                .background(color)
                .border(2.dp, borderColor)
                .padding(8.dp)
                .size(size)
        )
    }
}

enum class BoxState {
    COLLAPSED,
    EXPANDED
}

@Composable
fun KeyFrameAnimationSpecDemo() {

    var size by remember {
        mutableStateOf(1.dp)
    }
    val width by animateDpAsState(
        targetValue = size,
        animationSpec = keyframes {
            durationMillis = 2300
            50.dp at 500 with LinearOutSlowInEasing
            10.dp at 1000 with FastOutLinearInEasing
            20.dp at 1500 with LinearEasing
            80.dp at 2000 with FastOutLinearInEasing
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(Color.Red)
                .height(40.dp)
                .width(width)
        )
        Button(onClick = {
            size = 90.dp
        }) {
         Text(text = "Start Animations")
        }
    }
}