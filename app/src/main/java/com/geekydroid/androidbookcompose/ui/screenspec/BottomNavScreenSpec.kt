package com.geekydroid.androidbookcompose.ui.screenspec

import androidx.annotation.DrawableRes

sealed interface BottomNavScreenSpec : ScreenSpec
{

    companion object
    {
         val allScreens = ScreenSpec.allScreens.values.filterIsInstance<BottomNavScreenSpec>()
    }

    @get:DrawableRes
    val icon:Int

    val screenName:String
}