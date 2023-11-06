package com.padc.moments.mvp.interfaces

import android.content.Context
import android.graphics.Bitmap
import com.padc.moments.data.vos.PrivateMessageVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.data.vos.fcm.FCMBody
import com.padc.moments.mvp.views.ChatDetailView

interface ChatDetailPresenter : BasePresenter<ChatDetailView> {

    fun onTapGetImageButton()

    fun onTapOpenCameraButton()

    fun onTapGifButton()

    fun getUserId(): String
    fun sendMessage(senderId: String, receiverId: String, timeStamp: Long, message: PrivateMessageVO)

    fun getMessages(
        senderId: String,
        receiverId: String
    )

    fun uploadAndSendImage(bitmap: Bitmap)

    fun uploadGif(gifUrl:String,context:Context)

    fun sendGroupMessage(groupId: Long, timeStamp:Long, message:PrivateMessageVO)

    fun getGroupMessages(
        groupId:Long
    )

    fun sendFCMNotification(
        fcmBody: FCMBody
    )

    fun getSpecificUser(userId: String, onSuccess: (users: UserVO) -> Unit, onFailure: (String) -> Unit)
}