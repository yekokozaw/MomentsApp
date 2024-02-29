package com.padc.moments.network.storage

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage

fun subscribeToTopic(topic: String) {
    FirebaseMessaging.getInstance().subscribeToTopic(topic)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Subscribed to topic: $topic")
            } else {
                println("Subscription to topic $topic failed: ${task.exception?.message}")
            }
        }
}

fun sendMessageToTopic(topic: String, title: String, body: String) {
    val data = HashMap<String,String>()
    data["title"]= title
    data["body"] = body
    val message = RemoteMessage.Builder(topic)
        .setMessageId(java.lang.String.valueOf(System.currentTimeMillis()))
        .setData(data)
        .build()

    FirebaseMessaging.getInstance().send(message)
}
