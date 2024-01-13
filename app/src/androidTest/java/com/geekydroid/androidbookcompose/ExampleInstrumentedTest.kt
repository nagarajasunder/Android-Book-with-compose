package com.geekydroid.androidbookcompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.geekydroid.androidbookcompose.ui.theme.AndroidBookComposeTheme
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun greetingTest() {
        composeTestRule.setContent {
            AndroidBookComposeTheme {
                Greeting(name = "Naga")
            }
        }
        composeTestRule.onNodeWithText("Hello Naga!").assertIsDisplayed()
    }
}