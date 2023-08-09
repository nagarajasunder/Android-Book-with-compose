package com.geekydroid.androidbookcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.androidbookcompose.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationScope private val externalScope:CoroutineScope
) : ViewModel() {

    private val randomStr = MutableStateFlow<String>("0")
    private val randomStrVisibility = MutableStateFlow<Boolean>(true)

    private val _uiState : MutableStateFlow<UiState> = MutableStateFlow(UiState("0",true))
    val uiState = _uiState.asStateFlow()

    fun onUpdateStateClicked() {
        externalScope.launch(Dispatchers.Default) {
            delay(2500)
            _uiState.value = _uiState.value.copy(randomStr = Random.nextInt(1000).toString())
//            _uiState.update { currentState ->
//                currentState.copy(randomStr = Random.nextInt(1000).toString())
//            }
        }
    }

    fun onHideUiStateClicked() {
        externalScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(randomStrVisibility = !_uiState.value.randomStrVisibility)
//            _uiState.update { currentState ->
//                currentState.copy(randomStrVisibility = !currentState.randomStrVisibility)
//            }
        }
    }
}

data class UiState(
    val randomStr: String,
    val randomStrVisibility: Boolean
)