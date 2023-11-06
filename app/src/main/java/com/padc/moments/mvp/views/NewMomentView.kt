package com.padc.moments.mvp.views

import com.padc.moments.data.vos.UserVO

interface NewMomentView : BaseView {
    fun navigateToPreviousScreen()
    fun showGallery()
    fun showUserInformation(userList: List<UserVO>)
}