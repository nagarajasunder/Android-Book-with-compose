package com.geekydroid.androidbookcompose

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import com.zhuinden.flowcombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val data: MutableLiveData<UiResult<InvestmentOverview>> =
        MutableLiveData(UiResult.Loading())
    private val dateFilter: MutableLiveData<String> = MutableLiveData("")
    private val dataList = MutableLiveData(mutableStateListOf<String>())

    val uiData = combineTuple(
        data.asFlow(),
        dateFilter.asFlow(),
        dataList.asFlow()
    ).map { (uiResult, date,list) ->
        ScreenData(date, uiResult,list)
    }.asLiveData()

    fun postSuccess() {
        viewModelScope.launch {
            delay(1000)
            val listItems = dataList.value!!
            val randomChar = Random.nextInt(15)
            listItems.add("New Item $randomChar")
            dataList.value = listItems
        }
    }


    fun postError() {
        viewModelScope.launch {
            delay(1000)
            data.postValue(UiResult.Error())
        }
    }

}

data class InvestmentOverview(
    val totalInvestmentAmount: Double = 0.0,
    val investmentTypeOverview: List<String> = listOf(),
    val recentInvestments: List<Int> = listOf()
)

data class ScreenData(
    val date: String,
    val data: UiResult<InvestmentOverview>,
    val dataList:List<String>
) {
    companion object {
        val initialState = ScreenData("", UiResult.Loading(), listOf())
    }
}