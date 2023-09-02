package com.geekydroid.androidbookcompose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "SmsBroadcastReceiver"

@AndroidEntryPoint
class SmsBroadcastReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent!!.action) {
            val extras = intent.extras
            val status: Status? = extras?.getParcelable(SmsRetriever.EXTRA_STATUS) as Status?
            Toast.makeText(context,"Status ${status?.statusCode}",Toast.LENGTH_SHORT).show()
            when (status?.statusCode) {
                CommonStatusCodes.SUCCESS ->  {
                    val message = extras?.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
                    Toast.makeText(context!!,"Message Received, $message",Toast.LENGTH_SHORT).show()
                }

                CommonStatusCodes.TIMEOUT -> {
                    Toast.makeText(context!!,"Timeout",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}