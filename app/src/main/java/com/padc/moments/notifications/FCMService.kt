package com.padc.moments.notifications

import android.app.PendingIntent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.padc.moments.activities.MainActivity

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if(message.data["key1"] == "all" ) {
            message.notification?.let {
                NotificationUtils.sendNotification(
                    this,
                    it.body ?: "null",
                    it.title ?: "null",
                    pendingIntent = PendingIntent.getActivity(
                        this,
                        0,
                        MainActivity.newIntent(this),
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT
                    ),
                    type = message.data["key1"]!!
                )
            }
        }
        else {
            message.notification?.let {
                NotificationUtils.sendNotification(
                    this,
                    it.body ?: "null",
                    it.title ?: "null",
                    pendingIntent = PendingIntent.getActivity(
                        this,
                        0,
                        MainActivity.newIntent(this),
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT
                    ),
                    type = message.data["key1"]!!
                )
            }

//            message.data.let {
//                if (it.isNotEmpty()) {
//                    NotificationUtils.sendNotification(
//                        this,
//                        it["title"] ?: "_",
//                        it["body"] ?: "_",
//                        pendingIntent = PendingIntent.getActivity(
//                            this,
//                            0,
//                            MainActivity.newIntent(this),
//                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
//                        )
//                    )
//                }else{
//                    Log.d("noti data","data is null")
//                }
//            }
        }

    }
}