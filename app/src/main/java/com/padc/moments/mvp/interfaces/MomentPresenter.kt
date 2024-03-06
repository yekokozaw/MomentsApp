package com.padc.moments.mvp.interfaces

import com.padc.moments.data.vos.MomentVO
import com.padc.moments.delegates.MomentItemActionDelegate
import com.padc.moments.mvp.views.MomentView

interface MomentPresenter  : BasePresenter<MomentView> , MomentItemActionDelegate {
    fun onTapAddMomentButton()

    fun createMoment(moment:MomentVO,grade : String)

    fun getMomentType(type : String)
    fun deleteMoment(momentId: String)

    fun getUserId(): String

    fun getUserData()
    fun addLikedToMoment(momentId: String,likes : Map<String, String>)

    fun deleteLikedToMoment(momentId : String, likes: Map<String, String>)

    fun addMomentToUserBookmarked(currentUserId:String,moment: MomentVO)

    fun deleteMomentFromUserBookmarked(currentUserId: String,momentId:String)

    fun getMomentsFromUserBookmarked(currentUserId: String)
}