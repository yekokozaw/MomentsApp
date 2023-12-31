package com.padc.moments.data.models

import android.content.Context
import com.padc.moments.data.vos.TokenVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.network.auth.AuthManager
import com.padc.moments.network.auth.FirebaseAuthManager
import com.padc.moments.network.storage.PresenceManager
import com.padc.moments.persistence.MomentDatabase

object AuthenticationModelImpl : AuthenticationModel {

    override var mAuthManager: AuthManager = FirebaseAuthManager
    private var mMomentDatabase : MomentDatabase? = null

    fun initDatabase(context: Context){
        mMomentDatabase = MomentDatabase.getDbInstance(context)
    }

    override fun login(
        phoneNumber: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.login(phoneNumber,email, password, onSuccess, onFailure)
    }

    override fun register(
        userName: String,
        phoneNumber: String,
        email: String,
        password: String,
        birthDate: String,
        gender: String,
        imageUrl:String,
        fcmKey:String,
        onSuccess: (user: UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.register(userName, phoneNumber, email, password, birthDate, gender,imageUrl,fcmKey, onSuccess, onFailure)
    }

    override fun getUserId(): String {
        return mAuthManager.getUserId()
    }

    override fun addToken(token: TokenVO) {
        mMomentDatabase?.getDao()?.addToken(token)
    }

    override fun getToken(): TokenVO? {
        return mMomentDatabase?.getDao()?.getToken()
    }
}