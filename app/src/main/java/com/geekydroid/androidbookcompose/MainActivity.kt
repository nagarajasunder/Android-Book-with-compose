package com.geekydroid.androidbookcompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.geekydroid.androidbookcompose.ui.theme.AndroidBookComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import kotlin.random.Random

private const val TAG = "MainActivityy"
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
                    //SideEffectDemo()
                    //LaunchedEffectDemo()
                    //RememberCoroutineScopeDemo()
                    //RememberUpdatedStateDemo()
                    DisposableEffectDemo()
                }
            }
        }
    }
}

/**
 * The LaunchedEffect side effect will be called at the beginning of the composition
 * and won't be called again. We should give it a key and a block of code to execute.
 * The LaunchedEffect is called again if the key given is changed. We can call suspend
 * functions inside the LaunchedEffect
 */

@Composable
fun LaunchedEffectDemo()
{
    Log.d(TAG, "LaunchedEffectDemo: Recomposition in progress")

    var key by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = key, block = {
        launch {
            for (i in 0..5)
            {
                Log.d(TAG, "LaunchedEffectDemo: $i")
                delay(1000L)
            }
        }.invokeOnCompletion {
            Log.d(TAG, "LaunchedEffectDemo: completed ${it?.message}")
        }
    })

    var count by remember {
        mutableStateOf(0)
    }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Clicked $count times")
        Button(onClick = {
            //count++
            key++
        }) {
            Text(text = "Make state changes")
        }
    }
}

/**
 * SideEffect is a composable function that will be executed each and every time when the composition of
 * a compose completes successfully.
 * The Ideal usage is if we want to perform certain action at the end of each composition
 */

@Composable
fun SideEffectDemo()
{
    Log.d(TAG, "SideEffectDemo: Recomposition")

    var count by remember {
        mutableStateOf(0)
    }

    var enabled by remember {
        mutableStateOf(true)
    }

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Clicked $count times")
        Button(onClick = {
            count++
        },enabled = enabled) {
            Text(text = "Make state changes")
        }

    }

    SideEffect {
        CoroutineScope(Dispatchers.IO).launch {
            startTimer(5){
                Log.d(TAG, "SideEffectDemo: Timer End")
            }
        }
    }
}

/**
 * rememberCoroutineScope side effect gives us a coroutine scope that can be used in
 * places like callbacks. The coroutine scope is bound to the composable in which it is called
 * and it will be cancelled when the composable leaves the composition
 */

@Composable
fun RememberCoroutineScopeDemo(scaffoldState: ScaffoldState = rememberScaffoldState()) {
    Log.d(TAG, "rememberCoroutineScopeDemo: Recomposition in progress")
    val scope = rememberCoroutineScope()
    var changeState by remember {
        mutableStateOf(0)
    }
    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (changeState == 0) {
                Text(text = "Clicked times $changeState")
                Button(onClick = {
                    scope.launch {
                        launch {
                            for (i in 0..5) {
                                delay(500L)
                                if (i == 3) {
                                    changeState++
                                }
                                Log.d(TAG, "rememberCoroutineScopeDemo: corountines running $i")
                            }
                        }
                        launch {
                            delay(1000L)
                            Log.d(TAG, "rememberCoroutineScopeDemo: showing snack bar")
                            scaffoldState.snackbarHostState.showSnackbar("State changed")
                        }
                    }.invokeOnCompletion { error ->
                        Log.d(
                            TAG,
                            "rememberCoroutineScopeDemo: coroutine completed ${error?.message}"
                        )
                    }
                }) {
                    Text(text = "Click me")
                }
            } else {
                Button(onClick = { changeState = 0 }) {
                    Text(text = "Update state")
                }
            }
        }
    }
}

@Composable
fun RememberUpdatedStateDemo()
{
    TwoButtonScreen()
}

@Composable
fun TwoButtonScreen()
{
    var buttonColor by remember {
        mutableStateOf("Unknown")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red
            ),
            onClick = {
                Log.d(TAG, "TwoButtonScreen: Red button clicked")
                buttonColor = "Red"
            }) {
            Text(text = "Red button")
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black
            ),
            onClick = {
                Log.d(TAG, "TwoButtonScreen: Black button clicked")
                buttonColor = "Black"
            }) {
            Text(text = "Black button")
        }

        TimerScreen(buttonColor)
    }
}

/**
 * Disposable Effect is like LaunchedEffect will launch at the first composition and will be launched only if the
 * key changes. But in LaunchedEffect we cannot able to detect the cancellation. But in Disposable Effect an OnDispose
 * callback is called whenever the effect cancels. So we can make some post cancellation things there.
 */
@Composable
fun DisposableEffectDemo()
{
    var isTimerRunning by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    DisposableEffect(key1 = Unit, effect = {
        val job = CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "DisposableEffectDemo: Job 1 started")
            delay(5000L)
        }
        job.invokeOnCompletion {
            Log.d(TAG, "DisposableEffectDemo: job completed ${it?.message}")
        }
        val random = (1..10).random()
        Toast.makeText(context, "Timer started $random", Toast.LENGTH_SHORT).show()
        onDispose {
            Toast.makeText(context, "Timer ended $random", Toast.LENGTH_SHORT).show()
            job.cancel(CancellationException("cancelled due to state change"))
        }
    })

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            isTimerRunning = !isTimerRunning
        }) {
            Text(text = if (isTimerRunning) "Stop" else "Start")
        }
    }
}

@Composable
fun TimerScreen(buttonColor:String)
{
    val updatedColor by rememberUpdatedState(newValue = buttonColor)
    LaunchedEffect(key1 = Unit){
        startTimer(5){
            Log.d(TAG, "TimerScreen: last clicked button color $updatedColor")
        }
    }
}

suspend fun startTimer(seconds: Int,onTimerEnd:() -> Unit) {

    Log.d(TAG, "startTimer: Timer started")
    delay(seconds * 1000L)
    onTimerEnd()
}

fun getRandomInt() : Int
{
    val random = Random(5)
    return random.nextInt()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidBookComposeTheme {
        SideEffectDemo()
    }
}