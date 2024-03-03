package com.padc.moments.utils

// URL
const val BASE_URL = "https://api.giphy.com/"
const val BASE_URL_FCM = "https://fcm.googleapis.com/"

// GET
const val API_GET_GIPHY_TRENDING = "v1/gifs/trending"
const val API_GET_GIPHY_SEARCH = "/v1/gifs/search"

// POST
const val API_POST_FCM = "fcm/send"

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
const val FCM_AUTH_KEY = "key=AAAAEY3uckw:APA91bHKhYEmcSlsbF-3J_OFZDO2riuXOUm6WX9in-Yl7DYe1cWph2ZGuv4SRmo3IK5PbU2eo994AlIGv9oyDX_G3oaQJv9BDQw_JfEgn-5ARxTlF-yHsnrdyct8lOcUV9bUZc4RK6T5"

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