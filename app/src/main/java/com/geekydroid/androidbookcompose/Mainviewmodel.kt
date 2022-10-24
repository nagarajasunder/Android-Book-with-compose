package com.geekydroid.androidbookcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Mainviewmodel @Inject constructor(private val repo:MainRepository) : ViewModel() {

    private val _albumList:MutableLiveData<List<Album>> = MutableLiveData(listOf())
    val albumsList:LiveData<List<Album>> = _albumList

    fun getAllAlbums() {
        viewModelScope.launch {
            val result = repo.getAllAlbums()
            if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                _albumList.postValue(result.body())
            }
        }
    }


}