package com.geekydroid.androidbookcompose

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor() : ViewModel(), screenActions {

    private val showPermissionConcernDialog:MutableLiveData<Boolean> = MutableLiveData(false)
    private val launchPermissionRequest:MutableLiveData<Boolean> = MutableLiveData(false)
    val screenState:LiveData<screenState> = combine(showPermissionConcernDialog.asFlow(),launchPermissionRequest.asFlow()){ showPermissionDialog,launchPermissionReq ->
        screenState(showPermissionDialog,launchPermissionReq)
    }.asLiveData()

    override fun permissionConcernDialogDismissed() {
        showPermissionConcernDialog.value = false
        launchPermissionRequest.value = true
    }

    override fun requestPermissionButtonClicked() {
        showPermissionConcernDialog.value = true
    }

    override fun permissionsDeniedCompletely() {

    }

    override fun shouldShowRationaled() {
        launchPermissionRequest.value = false
    }

    override fun allPermissionsGranted() {
        launchPermissionRequest.value = false
    }
}

interface screenActions
{
    fun permissionConcernDialogDismissed()
    fun requestPermissionButtonClicked()
    fun permissionsDeniedCompletely()
    fun shouldShowRationaled()
    fun allPermissionsGranted()
}

data class screenState(val showPermissionConcernDialog:Boolean,val launchPermissionRequest:Boolean)
{
    companion object
    {
        val initialState = screenState(false,false)
    }
}

sealed class screenEvents
{
    object launchPermissionRequest : screenEvents()
}