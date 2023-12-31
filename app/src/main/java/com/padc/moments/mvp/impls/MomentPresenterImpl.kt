package com.padc.moments.mvp.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.MomentModelImpl
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.mvp.interfaces.MomentPresenter
import com.padc.moments.mvp.views.MomentView

class MomentPresenterImpl : MomentPresenter , ViewModel() {

    private var mView:MomentView? = null
    private val mMomentModel:MomentModel = MomentModelImpl
    private val mAuthModel:AuthenticationModel = AuthenticationModelImpl

    override fun initPresenter(view: MomentView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mMomentModel.getMoments(
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

    override fun onTapAddMomentButton() {
        mView?.navigateToNewMomentScreen()
    }

    override fun createMoment(moment: MomentVO) {
        mMomentModel.createMoment(moment)
    }

    override fun deleteMoment(momentId: String) {
        mMomentModel.deleteMoment(
            momentId,
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