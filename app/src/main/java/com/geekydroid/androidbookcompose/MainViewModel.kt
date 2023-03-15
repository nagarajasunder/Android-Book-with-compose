package com.geekydroid.androidbookcompose

import androidx.lifecycle.*
import com.zhuinden.flowcombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val data: MutableLiveData<UiResult<InvestmentOverview>> =
        MutableLiveData(UiResult.Loading())
    private val dateFilter: MutableLiveData<String> = MutableLiveData("")

    val uiData = combineTuple(
        data.asFlow(),
        dateFilter.asFlow()
    ).map { (uiResult, date) ->
        ScreenData(date, uiResult)
    }.asLiveData()

    fun postSuccess() {
        viewModelScope.launch {
            delay(1000)
            val investmentOverview = InvestmentOverview()
            data.postValue(UiResult.Success(investmentOverview))
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
    val data: UiResult<InvestmentOverview>
) {
    companion object {
        val initialState = ScreenData("", UiResult.Loading())
    }
}