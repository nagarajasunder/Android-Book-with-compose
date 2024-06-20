package com.geekydroid.androidbookcompose

import android.util.Log
import com.geekydroid.androidbookcompose.composables.Line
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.nio.charset.Charset

val message_type = "DRAW"

private const val TAG = "MyWebSocketListener"

interface SocketActions {
    fun onMessage(message: WSMessage)
    fun onMessage(message:Line)
}

class MyWebSocketListener (val socketActions:SocketActions) : WebSocketListener() {

    private var client: OkHttpClient? = null
    private lateinit var webSocket: WebSocket

    private var connection:WebSocket? = null
    private val gson = GsonBuilder().create()

    fun initWebsocket(url:String) {
        client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        webSocket = client!!.newWebSocket(request,this)

    }

    fun sendMessages(message:WSMessage) {
        connection?.let {conn ->
            val messageStr = gson.toJson(message,WSMessage::class.java)
            conn.send(messageStr)
        }
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        connection = webSocket
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d(TAG, "onMessage: $text")
        val wsMessage = gson.fromJson(text,WSMessage::class.java)
        if (wsMessage.messageType == message_type) {
            val drawMessage = wsMessage.message
            socketActions.onMessage(drawMessage)
        } else {
            socketActions.onMessage(wsMessage)
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
//        val message = bytes.string(Charset.defaultCharset())
//        val wsMessage = gson.fromJson(message,WSMessage::class.java)
//        if (wsMessage.messageType == message_type) {
//            val drawMessage = wsMessage.message as Line
//            socketActions.onMessage(drawMessage)
//        } else {
//            socketActions.onMessage(wsMessage)
//        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d(TAG, "onFailure: ${t.message}")
    }
}



data class WSMessage (
    @SerializedName("message_type")
    val messageType:String,
    @SerializedName("message")
    val message:Line
)