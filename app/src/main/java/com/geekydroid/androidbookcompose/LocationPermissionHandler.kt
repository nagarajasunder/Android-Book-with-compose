package com.geekydroid.androidbookcompose

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckAndShowLocationPermissions(callback: PermissionResultCallback,context:Context,isFromLandingScreen:Boolean = false)
{
    var launchPermission by remember {
        mutableStateOf(false)
    }
    if (buildVersionCheck(Build.VERSION_CODES.M,operation.LESS))
    {
        callback.permissionGranted()
    }
    else
    {
        if (checkForPermissions(context))
        {
            callback.permissionGranted()
        }
        else if (isPermissionsGranted(context,arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION)))
        {
            if (isFromLandingScreen)
            {
                callback.permissionGranted()
            }
            else
            {
                Log.d("xyz", "CheckAndShowLocationPermissions: show tutorial")
                ShowLocationPermissionTutorial{
                    goToAppSettings(context)
                }
            }
        }
        else
        {
            LaunchPermissionRequest(object : PermissionResultCallback{
                override fun permissionGranted() {
                    callback.permissionGranted()
                }

                override fun permissionDenied() {
                    callback.permissionDenied()
                }

            })
        }
    }
}

fun goToAppSettings(context:Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package",context.packageName,null)
    intent.data = uri
    context.startActivity(intent)
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun LaunchPermissionRequest(
    callback: PermissionResultCallback
) {

    val permissionList: List<String> = getPermissionListByVersion()
    val permissionRequest = rememberMultiplePermissionsState(permissions = permissionList) {
        var ct = 0
        it.forEach { entry ->
            if (entry.value) {
                ct++
            }
        }
        if (ct == it.size) {
            callback.permissionGranted()
        } else {
            callback.permissionDenied()
        }
    }
    SideEffect {
        permissionRequest.launchMultiplePermissionRequest()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShowLocationPermissionTutorial(onComplete:() -> Unit) {
    var showTutorial by remember {
        mutableStateOf(true)
    }
    if (showTutorial)
    {
        val imageList = arrayListOf(
            painterResource(id = R.drawable.permission_one),
            painterResource(id = R.drawable.permission_two),
            painterResource(id = R.drawable.permission_three)
        )
        HorizontalPager(count = 3, modifier = Modifier.fillMaxSize()) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = imageList[page], contentDescription = null)
                if (page == imageList.size - 1) {
                    Button(onClick = {
                        showTutorial = false
                        onComplete()
                    }) {
                        Text(text = "Done")
                    }
                }
            }
        }
    }
}

fun isPermissionsGranted(context:Context,permissions: ArrayList<String>): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
        for (permission in permissions)
        {
            if (ContextCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED)
            {
                return false
            }
        }
    }
    return true
}


fun getPermissionListByVersion(): List<String> {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
    {
        listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
    }
    else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q)
    {
        listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }
    else
    {
        listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}

fun checkForPermissions(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
    {
        ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    else
    {
        ((ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED))
    }
}

interface PermissionResultCallback
{
    fun permissionGranted()
    fun permissionDenied()
}

fun buildVersionCheck(versionCode:Int,operation: operation):Boolean
{
   when(operation)
   {
       com.geekydroid.androidbookcompose.operation.GREATER -> {
           return Build.VERSION.SDK_INT > versionCode
       }
       com.geekydroid.androidbookcompose.operation.EQUAL -> {
           return Build.VERSION.SDK_INT == versionCode
       }
       com.geekydroid.androidbookcompose.operation.LESS_THAN_EQUAL -> {
           return Build.VERSION.SDK_INT <= versionCode
       }
       com.geekydroid.androidbookcompose.operation.LESS -> {
           return Build.VERSION.SDK_INT < versionCode
       }
       com.geekydroid.androidbookcompose.operation.GREATER_THAN_EQUAL -> {
           return Build.VERSION.SDK_INT >= versionCode
       }
       com.geekydroid.androidbookcompose.operation.NOT_EQUAL -> {
           return Build.VERSION.SDK_INT != versionCode
       }
   }
}

enum class operation
{
    LESS,
    GREATER,
    EQUAL,
    LESS_THAN_EQUAL,
    GREATER_THAN_EQUAL,
    NOT_EQUAL
}