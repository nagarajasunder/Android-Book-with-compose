package com.geekydroid.androidbookcompose.ui.screenspec

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.geekydroid.androidbookcompose.ui.composables.ScreenBContent
import com.geekydroid.androidbookcompose.ui.screenspec.ScreenSpec


object ScreenBSpec : ScreenSpec {
    override val route: String
        get() = "screenb"

    @Composable
    override fun content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
        ScreenBContent(object : buttonClickCallback{
            override fun onButtonClick(screen: Screen) {
                when(screen)
                {
                    Screen.SCREEN_A -> {
                        navController.navigate(ScreenASpec.route){
                            popUpTo(ScreenASpec.route){
                                inclusive = true
                            }
                        }
                    }
                    Screen.SCREEN_B -> navController.navigate(ScreenBSpec.route)
                    Screen.SCREEN_C -> navController.navigate(ScreenCSpec.route)
                }
            }

        })
    }
}