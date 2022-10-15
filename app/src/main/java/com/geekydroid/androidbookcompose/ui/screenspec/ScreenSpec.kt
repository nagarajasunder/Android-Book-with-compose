package com.geekydroid.androidbookcompose.ui.screenspec

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink

sealed interface ScreenSpec
{
    companion object
    {
        val allScreens = listOf<ScreenSpec>(
            ScreenASpec,
            ScreenBSpec,
            ScreenCSpec
        ).associateBy {
            it.route
        }
    }

    val route:String
    val argument:List<NamedNavArgument>
        get() = emptyList()
    val deepLinks:List<NavDeepLink>
        get() = emptyList()

    @Composable
    fun content(navController: NavController,navBackStackEntry: NavBackStackEntry)
}

enum class Screen
{
    SCREEN_A,
    SCREEN_B,
    SCREEN_C
}

interface buttonClickCallback
{
    fun onButtonClick(screen:Screen)
}