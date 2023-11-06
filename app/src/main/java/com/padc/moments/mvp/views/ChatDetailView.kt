package com.padc.moments.mvp.views

import com.padc.moments.data.vos.GroupVO
import com.padc.moments.data.vos.PrivateMessageVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.network.retrofit.responses.FCMResponse

interface ChatDetailView : BaseView {
    fun showUsers(userList: List<UserVO>)
    fun showMessages(messageList: List<PrivateMessageVO>)
    fun showGallery()
    fun getImageUrlForFile(file: String)
    fun showGroupMessages(messageList: List<PrivateMessageVO>)
    fun getGroups(groupList: List<GroupVO>)
    fun openCamera()
    fun navigateToSearchGifsActivity()
    fun showFCMResponse(fcmResponse: FCMResponse)

}