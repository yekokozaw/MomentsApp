package com.padc.moments

import android.app.Application
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.network.auth.AuthManager
import com.padc.moments.network.auth.FirebaseAuthManager
import com.padc.moments.network.storage.PresenceManager

class Moments : Application() {

    override fun onCreate() {
        super.onCreate()
        AuthenticationModelImpl.initDatabase(applicationContext)
    }

}