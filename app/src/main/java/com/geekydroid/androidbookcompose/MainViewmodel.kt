package com.geekydroid.androidbookcompose

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.opencsv.CSVReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.*
import javax.inject.Inject

private const val TAG = "MainViewmodel"

@HiltViewModel
class MainViewmodel @Inject constructor(application: Application) : AndroidViewModel(application),ScreenActions {

    private val eventsChannel:Channel<ScreenEvents> = Channel()
    val events = eventsChannel.receiveAsFlow()
    private val _importedData:MutableLiveData<String> = MutableLiveData("")
    val importedData:LiveData<String> = _importedData

    override fun onOpenSafClicked() {
        viewModelScope.launch {
            eventsChannel.send(ScreenEvents.OpenFilePicker)
        }
    }

    fun readCsv(uri: Uri) {
        val context = getApplication<AndroidBook>().applicationContext
       val parcelFileDescriptor =
           context.contentResolver.openFileDescriptor(
               uri,
               "r",
               null
           )
        val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
        val fileName = context.contentResolver.getFileName(uri)
        val file = File(context.cacheDir,fileName)
        val outputStream = FileOutputStream(file)
        copyData(inputStream,outputStream)
        val data = importData(file)
        _importedData.value = data
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
