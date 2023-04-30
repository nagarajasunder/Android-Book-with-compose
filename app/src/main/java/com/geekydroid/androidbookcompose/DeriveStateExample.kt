package com.geekydroid.androidbookcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun DerivedStateExample(items: List<String>) {

    val liststate = rememberLazyListState()
    LazyColumn(state = liststate) {
        items(items.size) { index ->
            Text(text = items[index], modifier = Modifier.padding(8.dp))
        }
    }

    val showButton = remember {
        derivedStateOf {
            liststate.firstVisibleItemIndex > 0
        }
    }
    AnimatedVisibility(visible = showButton.value) {
        Button(onClick = {}) {
            Text(text = "Button ${Random(50).nextInt()}")
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun DeriveStateExamplePreview() {
    val list = mutableListOf<String>()
    repeat(100) {
        list.add("Task $it")
    }
    DerivedStateExample(items = list)
}