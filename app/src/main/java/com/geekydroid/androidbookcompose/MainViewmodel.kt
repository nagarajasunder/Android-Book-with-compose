package com.geekydroid.androidbookcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(private val cryptoManager: CryptoManager) : ViewModel(),ScreenActions {

    private val valueToStore:MutableLiveData<String> = MutableLiveData("")
    private val dummy:MutableLiveData<String> = MutableLiveData("")
    private val _screenState:MutableLiveData<ScreenState> = MutableLiveData(ScreenState.initialState)
    val screenState:LiveData<ScreenState> = _screenState

    override fun onSubmitButtonClicked() {
        viewModelScope.launch {
            if (!valueToStore.value.isNullOrEmpty())
            {
                cryptoManager.secureStore(valueToStore.value!!)
                _screenState.postValue(ScreenState("Encryption Successful"))
            }
        }
    }

    override fun onTextValueChange(newValue: String) {
        valueToStore.value = newValue
        val state = ScreenState(newValue)
        _screenState.postValue(state)
    }

    override fun onDecryptButtonClicked() {
        viewModelScope.launch {
            cryptoManager.secureFetch().collect{
                _screenState.postValue(ScreenState(it))
            }
        }
    }


}

data class ScreenState(val value:String)
{
    companion object{
        val initialState = ScreenState("")
    }
}

interface ScreenActions
{
    fun onSubmitButtonClicked()
    fun onTextValueChange(newValue:String)
    fun onDecryptButtonClicked()
}