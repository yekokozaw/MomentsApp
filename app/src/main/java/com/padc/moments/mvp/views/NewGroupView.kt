package com.padc.moments.mvp.views

import com.padc.moments.data.vos.UserVO

interface NewGroupView : BaseView {


    fun addUserToGroup(userId: String, isCheck: Boolean)

    fun navigateToChatDetailScreen(userId: String)
    fun showContacts(userList: List<UserVO>)

    fun showGallery()
    fun openCamera()
    fun getGroupCoverImageUrl(imageUrl: String)
}