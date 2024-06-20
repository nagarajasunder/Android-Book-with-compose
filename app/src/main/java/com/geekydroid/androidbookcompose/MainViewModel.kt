package com.geekydroid.androidbookcompose

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.androidbookcompose.composables.Line
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScreenData(
    val lines: SnapshotStateList<Line> = mutableStateListOf()
)

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel(), SocketActions {

    private lateinit var socketListener: MyWebSocketListener
    private val _screenState = MutableStateFlow(ScreenData())
    val screenState:StateFlow<ScreenData> = _screenState


    fun onDraw(line:Line) {
        val message = WSMessage(messageType = message_type,line)
        socketListener.sendMessages(message)
    }

    fun connectToWebSocket() {
        val userName = System.currentTimeMillis().toString()
        socketListener = MyWebSocketListener(this)
        socketListener.initWebsocket("ws://192.168.1.9:3000/room/62f93324-6a4b-4242-b3de-2477fea7606e/players/giara/create")
    }

    override fun onMessage(message: WSMessage) {

    }

    override fun onMessage(message: Line) {
//        val updatedLines = _screenState.value.lines
//        updatedLines.add(message)
//
//        val updatedState = _screenState.value.copy(lines = updatedLines)
//        _screenState.update {
//            updatedState
//        }
       viewModelScope.launch {
           _screenState.update {
               val newList = mutableStateListOf<Line>()
               newList.addAll(it.lines)
               newList.add(message)
               it.lines.clear()
               it.copy(lines = newList)
           }
       }
    }
}