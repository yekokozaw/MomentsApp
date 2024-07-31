package com.padc.moments.utils

import android.content.Context
import android.widget.Toast

// URL
const val BASE_URL = "https://api.giphy.com/"
const val BASE_URL_FCM = "https://fcm.googleapis.com/"

// GET
const val API_GET_GIPHY_TRENDING = "v1/gifs/trending"
const val API_GET_GIPHY_SEARCH = "/v1/gifs/search"

// POST
const val API_POST_FCM = "v1/projects/social-app-fb7b9/messages:send"

// PARAM
const val PARAM_API_KEY = "api_key"
const val PARAM_API_KEY_SEARCH = "q"
const val PARAM_API_LIMIT = "limit"

// HEADERS
const val HEADER_AUTH = "Authorization"
//const val HEADER_CONTENT_TYPE = "Content-Type"
//const val HEADER_CONTENT_LENGTH = "Content-Length"
//const val HEADER_HOST = "Host"
//const val HEADER_USER_AGENT = "User-Agent"
//const val HEADER_ACCEPT = "Accept"
//const val HEADER_ACCEPT_ENCODING = "Accept-Encoding"
//const val HEADER_CONNECTION = "Connection"

// Keys
const val GIPHY_API_KEY = "fNcKKGdZ8bta2IdgEAA1PTwLe6VbS1I8"
const val FCM_AUTH_KEY = "Bearer ya29.a0AXooCgsTxqeOtMRk-S2F8OiS94NdXRvsscHiSLOpXbN7B98yr0ZR36OWlK2MyOXX3qmE5yXFNxViXhNw6upt6_Yp0TdROucJaPURDlghC1jqLVYKUMMTkW2XJPRXSN5O0sDURvujKr9nu7rkEKxKV7XCkU1ppzVHEgP3aCgYKAcMSARASFQHGX2MimbX3zzGRTK_uDowkHNXyZQ0171"

fun getTimeAgo(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val difference = now - timestamp

    val seconds = difference / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 0 -> "$days days ago"
        hours > 0 -> "$hours hours ago"
        minutes > 0 -> "$minutes mins ago"
        else -> "just now"
    }
}

fun makeToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}