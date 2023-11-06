package com.padc.moments.mvp.interfaces

import com.padc.moments.delegates.ChatItemActionDelegate
import com.padc.moments.delegates.GroupItemActionDelegate
import com.padc.moments.mvp.views.ChatView

interface ChatPresenter  : BasePresenter<ChatView> , ChatItemActionDelegate , GroupItemActionDelegate {
    fun getContacts(scannerId:String)

    fun getUserId() : String

    fun getChatHistoryUserId(
        senderId: String
    )

    fun getLastMessage(
        senderId: String,
        receiverId: String
    )
    fun getGroupMessages(groupId: Long, onSuccess: (Int) -> Unit)
}