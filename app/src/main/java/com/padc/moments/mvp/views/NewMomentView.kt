package com.padc.moments.mvp.views

import com.padc.moments.data.vos.UserVO
import com.padc.moments.data.vos.fcm.FCMBody

interface NewMomentView : BaseView {
    fun navigateToPreviousScreen()
    fun showGallery()

    fun finishFragment(topic : String)
    fun showUserInformation(userList: List<UserVO>)

    fun imageReady(ready : Boolean)

}