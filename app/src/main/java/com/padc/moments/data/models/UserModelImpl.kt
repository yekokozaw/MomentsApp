package com.padc.moments.data.models

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.padc.moments.data.vos.TokenVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.data.vos.fcm.FCMBody
import com.padc.moments.network.retrofit.responses.FCMResponse
import com.padc.moments.network.storage.CloudFireStoreFirebaseApiImpl
import com.padc.moments.network.storage.CloudFireStoreFirebaseApi
import com.padc.moments.persistence.MomentDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserModelImpl : UserModel , RetrofitAbstractBaseModel() {

    override var mFirebaseApi: CloudFireStoreFirebaseApi = CloudFireStoreFirebaseApiImpl

    override fun addUser(user: UserVO) {
        mFirebaseApi.addUser(user)
    }

    override fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO) {
        mFirebaseApi.updateAndUploadProfileImage(bitmap,user)
    }

    override fun getUsers(onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getUsers(onSuccess = onSuccess , onFailure = onFailure)
    }

    override fun getSpecificUser(userId: String,onSuccess: (users: UserVO) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getSpecificUser(userId,onSuccess,onFailure)
    }

    override fun createContact(scannerId:String,qrExporterId:String,contact: UserVO) {
        mFirebaseApi.createContact(scannerId,qrExporterId,contact)
    }

    override fun getContacts(
        scannerId: String,
        onSuccess: (users: List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getContacts(scannerId, onSuccess, onFailure)
    }

    override fun sendFCMNotification(
        fcmBody: FCMBody,
        onSuccess: (FCMResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        Log.i("FCMModel",fcmBody.toString())
        mFCMApi.sendFCMNotification(
            fcmBody = fcmBody
        ).enqueue(object : Callback<FCMResponse> {
            override fun onResponse(
                call: Call<FCMResponse>,
                response: Response<FCMResponse>
            ) {
                if (response.isSuccessful) {
                    val ticket = response.body()
                    if (ticket != null) {
                        onSuccess(ticket)
                    }
                } else {
                    onFailure("Don't make errors")
                }
            }

            override fun onFailure(call: Call<FCMResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun uploadFCMToken(userId: String, token : String) {
        mFirebaseApi.updateFCMToken(userId,token)
    }


}