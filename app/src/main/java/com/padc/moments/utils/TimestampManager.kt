package com.padc.moments.utils

import android.content.Context
import android.content.SharedPreferences

class TimestampManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("timestamp_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TIMESTAMP_KEY = "timestamp_key"
    }

    // Store a timestamp
    fun storeTimestamp(timestamp: Long) {
        sharedPreferences.edit().putLong(TIMESTAMP_KEY, timestamp).apply()
    }

    // Retrieve the stored timestamp
    fun getTimestamp(): Long {
        return sharedPreferences.getLong(TIMESTAMP_KEY, 0) // 0 is the default value if the key doesn't exist
    }

}