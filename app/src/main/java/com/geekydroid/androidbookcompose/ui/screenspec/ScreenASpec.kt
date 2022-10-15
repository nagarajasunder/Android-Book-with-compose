package com.geekydroid.androidbookcompose.ui.screenspec

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.geekydroid.androidbookcompose.ui.composables.ScreenAContent

object ScreenASpec : ScreenSpec{
    override val route: String
        get() = "screena"

    @Composable
    override fun content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
        ScreenAContent(object : buttonClickCallback{
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