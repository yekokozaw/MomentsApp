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
import com.padc.moments.data.vos.UserVO
import com.padc.moments.mvp.interfaces.ProfilePresenter
import com.padc.moments.mvp.views.ProfileView

class ProfilePresenterImpl : ProfilePresenter , ViewModel() {

    override var mAuthModel: AuthenticationModel = AuthenticationModelImpl
    override var mUserModel: UserModel = UserModelImpl
    override var mMomentModel: MomentModel = MomentModelImpl

    private var mView:ProfileView? = null
    override fun initPresenter(view: ProfileView) {
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

    override fun onTapBookmarkButton(id: String,isBookmarked:Boolean) {
        mView?.getMomentIsBookmarked(id,isBookmarked)
    }

    override fun onTapOptionButton(momentId:String,momentOwnerUserId:String) {
        mView?.showOptionDialogBox(momentId,momentOwnerUserId)
    }

    override fun onTapOpenCameraButton() {
        mView?.openCamera()
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun updateUserInformation(user: UserVO) {
        mUserModel.addUser(user)
    }

    override fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO) {
        mUserModel.updateAndUploadProfileImage(bitmap,user)
    }

    override fun onTapEditProfileButton() {
        mView?.showEditProfileDialog()
    }

    override fun onTapQrCodeImage() {
        mView?.showQrCodeDialog()
    }

    override fun onTapGalleryImage() {
        mView?.showGallery()
    }

    override fun createMoment(moment: MomentVO) {
        mMomentModel.createMoment(moment)
    }

    override fun getMomentsFromUserBookmarked(currentUserId: String) {
        mMomentModel.getMomentsFromUserBookmarked(
            currentUserId,
            onSuccess = {
                mView?.showMoments(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun deleteMomentFromUserBookmarked(currentUserId: String, momentId: String) {
        mMomentModel.deleteMomentFromUserBookmarked(currentUserId, momentId)
    }
}