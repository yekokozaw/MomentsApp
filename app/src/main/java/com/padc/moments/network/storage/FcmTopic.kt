package com.padc.moments.network.storage

import android.content.Context
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

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

fun unsubscribeFromTopic(topic: String){
    FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Unsubscribed to topic: $topic")
            } else {
                println("Unsubscribe to topic $topic failed: ${task.exception?.message}")
            }
        }
}

fun getServiceAccountJson(context: Context): String {
    val inputStream = context.assets.open("social-app.json")
    val reader = InputStreamReader(inputStream)
    return reader.readText()
}

fun getAccessToken(context: Context, callback: (String) -> Unit) {
    Thread {
        try {
            val json = getServiceAccountJson(context)
            val credentials = ServiceAccountCredentials.fromStream(ByteArrayInputStream(json.toByteArray()))
            val googleCredentials = credentials.createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
            googleCredentials.refreshIfExpired()
            val accessToken = googleCredentials.accessToken.tokenValue
            callback(accessToken)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.start()
}

fun sendMessageToTopic(accessToken: String, topic: String, title: String, body: String) {
    val client = OkHttpClient()

    val json = """
        {
          "message": {
            "topic": "$topic",
            "notification": {
              "title": "$title",
              "body": "$body"
            },
            "data": {
              "key1": "$topic"
            }
          }
        }
    """.trimIndent()

    val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    val request = Request.Builder()
        .url("https://fcm.googleapis.com/v1/projects/social-app-fb7b9/messages:send")
        .addHeader("Authorization", "Bearer $accessToken")
        .addHeader("Content-Type", "application/json; UTF-8")
        .post(requestBody)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }
        println(response.body?.string())
    }
}