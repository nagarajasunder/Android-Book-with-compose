package com.geekydroid.androidbookcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.geekydroid.androidbookcompose.ui.screenspec.ScreenSpec
import com.geekydroid.androidbookcompose.ui.theme.AndroidBookComposeTheme

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
                    ScreenContent()
                }
            }
        }
    }

}

@Composable
fun ScreenContent() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    NavHost(
        navController = navController,
        startDestination = ScreenSpec.allScreens.values.toList()[0].route
    ) {
        ScreenSpec.allScreens.values.forEach { screen ->
            composable(
                route = screen.route,
                arguments = screen.argument,
                deepLinks = screen.deepLinks
            ) { navBackStackEntry ->
                screen.content(
                    navController = navController,
                    navBackStackEntry = navBackStackEntry
                )
            }
        }
    }
}