package com.padc.moments.mvp.views

import com.padc.moments.data.vos.MomentVO

interface MomentView  : BaseView {
    fun navigateToNewMomentScreen()
    fun showMoments(momentList: List<MomentVO>)
    fun getMomentIsBookmarked(id: String,isBookmarked:Boolean)
    fun showOptionDialogBox(momentId:String,momentOwnerUserId:String)
    fun showMomentsFromBookmarked(momentList: List<MomentVO>)
    fun showDeleteSuccessfulMessage(successfulMessage: String)
}