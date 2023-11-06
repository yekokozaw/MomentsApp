package com.padc.moments.mvp.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.MomentModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.mvp.interfaces.NewMomentPresenter
import com.padc.moments.mvp.views.NewMomentView

class NewMomentPresenterImpl: NewMomentPresenter , ViewModel() {

    private var mView:NewMomentView? = null

    override var mAuthModel: AuthenticationModel = AuthenticationModelImpl
    override var mMomentModel: MomentModel = MomentModelImpl
    override var mUserModel: UserModel = UserModelImpl

    override fun initPresenter(view: NewMomentView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mUserModel.getUsers(
            onSuccess = {
                mView?.showUserInformation(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapAddImageButton() {
        mView?.showGallery()
    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapCreateButton(moment:MomentVO) {
        mMomentModel.createMoment(moment)
    }

    override fun createMomentImages(bitmap: Bitmap) {
        mMomentModel.updateAndUploadMomentImage(bitmap)
    }

    override fun getMomentImages() :String {
        return mMomentModel.getMomentImages()
    }

    override fun clearMomentImages() {
        mMomentModel.clearMomentImages()
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

}