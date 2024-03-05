package com.padc.moments.mvp.views

import com.padc.moments.data.vos.MomentVO
import com.padc.moments.data.vos.UserVO

interface MomentView  : BaseView {
    fun navigateToNewMomentScreen()
    fun showMoments(momentList: List<MomentVO>)

    fun navigateToImageDetails(image : String)
    fun getUserData(user: UserVO)
    fun navigateToCommentScreen(momentId: String)
    fun getMomentIsLiked(id: String, likes : Map<String,String>,isLike : Boolean)
    fun getMomentIsBookmarked(id: String,isBookmarked:Boolean)
    fun showOptionDialogBox(momentId:String,momentOwnerUserId:String)
    fun showMomentsFromBookmarked(momentList: List<MomentVO>)
    fun showDeleteSuccessfulMessage(successfulMessage: String)
}