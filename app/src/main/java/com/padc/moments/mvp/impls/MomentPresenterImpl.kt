package com.padc.moments.mvp.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.MomentModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.mvp.interfaces.MomentPresenter
import com.padc.moments.mvp.views.MomentView

class MomentPresenterImpl : MomentPresenter , ViewModel() {

    private var mView:MomentView? = null
    private var userName : String = ""
    private var momentType : String = ""
    private val mMomentModel:MomentModel = MomentModelImpl
    private val mUserModel : UserModel = UserModelImpl
    private val mAuthModel:AuthenticationModel = AuthenticationModelImpl
    override fun initPresenter(view: MomentView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mMomentModel.getMoments(
            type = momentType,
            onSuccess =  {
                mView?.showMoments(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapBookmarkButton(id: String,isBookmarked:Boolean) {
        mView?.getMomentIsBookmarked(id,isBookmarked)
    }

    override fun onTapOptionButton(momentId:String,momentOwnerUserId:String) {
        mView?.showOptionDialogBox(momentId,momentOwnerUserId)
    }

    override fun onLongClickImage(imageUrl: String) {
        mView?.navigateToImageDetails(imageUrl)
    }

    override fun onTapCommentButton(momentId: String) {
        mView?.navigateToCommentScreen(momentId)
    }

    override fun onTapLikeButton(momentId: String,likes : Map<String,String>,isLike : Boolean) {
        mView?.getMomentIsLiked(momentId,likes,isLike)
    }

    override fun onTapAddMomentButton() {
        mView?.navigateToNewMomentScreen()
    }

    override fun createMoment(moment: MomentVO,grade : String) {
        mMomentModel.createMoment(moment,grade)
    }

    override fun getMomentType(type: String) {
        momentType = type
    }

    override fun deleteMoment(momentId: String,grade: String) {
        mMomentModel.deleteMoment(
            grade = grade,
            momentId = momentId,
            onSuccess = {
                mView?.showDeleteSuccessfulMessage(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun addLikedToMoment(momentId: String,likes : Map<String, String>,grade: String) {
        val likeList = likes + Pair(getUserId(),userName)
        mMomentModel.addLikedToMoment(momentId = momentId, likeList,grade)
    }

    override fun getUserData() {
        mUserModel.getSpecificUser(
            mAuthModel.getUserId(),
            onSuccess = {
                userName = it.userName
                mView?.getUserData(it)

            }, onFailure = {

            })
    }
    override fun deleteLikedToMoment(momentId: String, likes: Map<String, String>,grade: String) {
        val likeList = likes - getUserId()
        mMomentModel.addLikedToMoment(momentId = momentId, likeList,grade)
    }

    override fun addMomentToUserBookmarked(currentUserId: String, moment: MomentVO) {
        mMomentModel.addMomentToUserBookmarked(currentUserId,moment)
    }

    override fun deleteMomentFromUserBookmarked(currentUserId: String, momentId: String) {
        mMomentModel.deleteMomentFromUserBookmarked(currentUserId, momentId)
    }

    override fun getMomentsFromUserBookmarked(currentUserId: String) {
        mMomentModel.getMomentsFromUserBookmarked(
            currentUserId,
            onSuccess = {
                mView?.showMomentsFromBookmarked(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }
}