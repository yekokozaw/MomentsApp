package com.padc.moments.mvp.interfaces

import com.padc.moments.data.vos.MomentVO
import com.padc.moments.delegates.MomentItemActionDelegate
import com.padc.moments.mvp.views.MomentView

interface MomentPresenter  : BasePresenter<MomentView> , MomentItemActionDelegate {
    fun onTapAddMomentButton()

    fun createMoment(moment:MomentVO)

    fun deleteMoment(momentId: String)

    fun getUserId(): String

    fun addMomentToUserBookmarked(currentUserId:String,moment: MomentVO)

    fun deleteMomentFromUserBookmarked(currentUserId: String,momentId:String)

    fun getMomentsFromUserBookmarked(currentUserId: String)
}