package com.geekydroid.androidbookcompose

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.opencsv.CSVReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.*
import javax.inject.Inject

private const val TAG = "MainViewmodel"

@HiltViewModel
class MainViewmodel @Inject constructor(
    private val datastore: DataStore<Preferences>,
    application: Application
) : AndroidViewModel(application), ScreenActions {

    private val eventsChannel:Channel<ScreenEvents> = Channel()
    val events = eventsChannel.receiveAsFlow()
    private val _importedData:MutableLiveData<String> = MutableLiveData("")
    val importedData:LiveData<String> = _importedData
    val savedUriData:MutableLiveData<String> = MutableLiveData("")

    override fun onOpenSafClicked() {
        viewModelScope.launch {
            eventsChannel.send(ScreenEvents.OpenFilePicker)
        }
    }

    override fun accessDataFromSavedUri() {
       viewModelScope.launch {
           val uri = getSavedUri().first()
           delay(1000L)
           _importedData.value = "$uri value"
           val uriP = Uri.parse(savedUriData.value!!)
           getDataFromUri(uriP)
       }
    }

    private fun getSavedUri(): Flow<String> {
        val key = stringPreferencesKey("uri_key")
        return datastore.data.map { prefs ->
            prefs[key]?:""
        }
    }

    fun readCsv(uri: Uri) {
//        val context = getApplication<AndroidBook>().applicationContext
//       val parcelFileDescriptor =
//           context.contentResolver.openFileDescriptor(
//               uri,
//               "r",
//               null
//           )
//        val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
//        val fileName = context.contentResolver.getFileName(uri)
//        val file = File(context.cacheDir,fileName)
//        val outputStream = FileOutputStream(file)
//        copyData(inputStream,outputStream)
//        val data = importData(file)
//        _importedData.value = data
        saveUri(uri)
        getDataFromUri(uri)
    }

    private fun saveUri(uri: Uri) {
        viewModelScope.launch {
            val key = stringPreferencesKey("uri_key")
            datastore.edit {
                it[key] = uri.toString()
            }
        }
    }

    private fun getDataFromUri(uri: Uri) {
        try {
            val context = getApplication<AndroidBook>().applicationContext
            val builder = StringBuilder()
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BufferedReader(
                   InputStreamReader(inputStream)
                ).use { reader ->
                    var line = reader.readLine()
                    while (line != null)
                    {
                        builder.append(line)
                        line = reader.readLine()
                    }

                }
            }
            _importedData.postValue(builder.toString())
        }
        catch (e:Exception)
        {
            _importedData.postValue(e.message?:"error happened")
        }
    }

    private fun importData(file: File): String {

        val extractedData = StringBuilder("")
       try {
           //_importedData.value = file.absolutePath
           val reader = CSVReader(FileReader(file.absolutePath))
           while (true)
           {
               val next = reader.readNext()
               if (next != null)
               {
                   next.forEach {
                       extractedData.append(it)
                   }
               }
               else
               {
                   break
               }
           }
       }
       catch (e:Exception)
       {
           return e.message?:"error occured"
       }

        return extractedData.toString()
    }



}


sealed class ScreenEvents
{
    object OpenFilePicker : ScreenEvents()
}

interface ScreenActions
{
    fun onOpenSafClicked()
    fun accessDataFromSavedUri()
}

fun ContentResolver.getFileName(uri:Uri):String
{
    var fileName = ""
    val cursor = query(uri,null,null,null,null)
    if (cursor != null)
    {
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        fileName = cursor.getString(nameIndex)
        cursor.close()
    }
    return fileName
}

@Throws(IOException::class)
fun copyData(ips: InputStream, out: OutputStream) {
    try {
        var numRead = 0
        val buf = ByteArray(102400)
        var total: Long = 0
        while (ips.read(buf).also { numRead = it } >= 0) {
            total += numRead.toLong()
            out.write(buf, 0, numRead)

            //flush after 1MB, so as heap memory doesn't fall short
            if (total > 1024 * 1024) {
                total = 0
                out.flush()
            }
        }
        out.close()
        ips.close()
    } catch (e: IOException) {
    }
}
