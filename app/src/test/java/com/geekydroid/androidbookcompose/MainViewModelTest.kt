package com.geekydroid.androidbookcompose

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `viewmodel default value test`() = runTest {
        val viewmodel = MainViewmodel()
        assertThat(viewmodel.screenState.value.categories).isEqualTo(listOf(""))
    }

}