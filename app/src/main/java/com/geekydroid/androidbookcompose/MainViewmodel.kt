package com.geekydroid.androidbookcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor() : ViewModel() {

    private val _screenState:MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState())
    val screenState:StateFlow<ScreenState> = _screenState

    private var expenditureTypes:List<String> = listOf()

    init {
        getExpenditureTypes()
    }

    private fun getExpenditureTypes() {
        viewModelScope.launch {
            getExpendituresTys {
                expenditureTypes = it
                getExpenditureCategories()
            }
        }
    }

    private fun getExpenditureCategories() {
        viewModelScope.launch {
            val expenditureCategory:String = expenditureTypes.find { it == _screenState.value.expenditureType }!!
            getExpendituresCts(expenditureCategory) {categories ->
                _screenState.update {
                    it.copy(categories = categories)
                }
            }
        }
    }

    suspend fun getExpendituresTys(onSuccess: (List<String>) -> Unit) {
        delay(2000)
        onSuccess(listOf("EXPENSE","INCOME"))
    }
    suspend fun getExpendituresCts(category:String,onSuccess: (List<String>) -> Unit) {
        delay(2000)
        onSuccess(listOf("ELECTRICITY","RENT"))
    }





}