package com.padc.moments.mvp.views

import com.padc.moments.data.vos.MomentVO
import com.padc.moments.data.vos.UserVO

interface ProfileView  : BaseView {

    fun showUserInformation(userList:List<UserVO>)

    fun showEditProfileDialog()
    fun showQrCodeDialog()
    fun showGallery()
    fun showMoments(momentList: List<MomentVO>)
    fun getMomentIsBookmarked(id: String, bookmarked: Boolean)
    fun showOptionDialogBox(momentId: String, momentOwnerUserId: String)
    fun openCamera()
}