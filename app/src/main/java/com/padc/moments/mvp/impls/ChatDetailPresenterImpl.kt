package com.padc.moments.mvp.impls

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.ChatModel
import com.padc.moments.data.models.ChatModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.data.vos.PrivateMessageVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.data.vos.fcm.FCMBody
import com.padc.moments.mvp.interfaces.ChatDetailPresenter
import com.padc.moments.mvp.views.ChatDetailView

class ChatDetailPresenterImpl : ChatDetailPresenter, ViewModel() {

    private var mView: ChatDetailView? = null
    private val mChatModel: ChatModel = ChatModelImpl
    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl
    private val mUserModel: UserModel = UserModelImpl

    override fun initPresenter(view: ChatDetailView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mUserModel.getUsers(
            onSuccess = {
                mView?.showUsers(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )

        mChatModel.getGroups(
            onSuccess = {
                mView?.getGroups(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapGetImageButton() {
        mView?.showGallery()
    }

    override fun onTapOpenCameraButton() {
        mView?.openCamera()
    }

    override fun onTapGifButton() {
        mView?.navigateToSearchGifsActivity()
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun sendMessage(
        senderId: String,
        receiverId: String,
        timeStamp: Long,
        message: PrivateMessageVO
    ) {
        mChatModel.sendMessage(senderId, receiverId, timeStamp, message)
    }

    override fun getMessages(senderId: String, receiverId: String) {
        mChatModel.getMessages(
            senderId,
            receiverId,
            onSuccess = { messageList ->
                val sortedMessages = messageList.sortedBy { it.timeStamp }
                mView?.showMessages(sortedMessages)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun uploadAndSendImage(
        bitmap: Bitmap
    ) {
        mChatModel.uploadAndSendImage(
            bitmap,
            onSuccess = {
                mView?.getImageUrlForFile(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun uploadGif(gifUrl: String, context: Context) {
        mChatModel.uploadGif(
            gifString = gifUrl,
            context = context,
            onSuccess = {
                mView?.getImageUrlForFile(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun sendGroupMessage(groupId: Long, timeStamp: Long, message: PrivateMessageVO) {
        mChatModel.sendGroupMessage(groupId, timeStamp, message)
    }

    override fun getGroupMessages(groupId: Long) {
        mChatModel.getGroupMessages(
            groupId,
            onSuccess = { messageList ->
                val sortedMessages = messageList.sortedBy { it.timeStamp }
                mView?.showGroupMessages(sortedMessages)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun sendFCMNotification(
        fcmBody: FCMBody
    ) {
        mUserModel.sendFCMNotification(
            fcmBody,
            onSuccess = {
                mView?.showFCMResponse(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun getSpecificUser(userId: String, onSuccess: (users: UserVO) -> Unit, onFailure: (String) -> Unit) {
        mUserModel.getSpecificUser(
            userId,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {
                onFailure(it)
            }
        )
    }
}