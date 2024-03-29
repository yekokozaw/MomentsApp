package com.padc.moments

import android.app.Application
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.utils.TimestampManager

class Moments : Application() {

    private lateinit var mTimeStampManager : TimestampManager
    override fun onCreate() {
        super.onCreate()
        mTimeStampManager = TimestampManager(applicationContext)
        AuthenticationModelImpl.initDatabase(applicationContext)
    }


}