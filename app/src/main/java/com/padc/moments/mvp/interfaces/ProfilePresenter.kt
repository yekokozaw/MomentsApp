package com.padc.moments.mvp.interfaces

import android.graphics.Bitmap
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.delegates.MomentItemActionDelegate
import com.padc.moments.mvp.views.ProfileView

interface ProfilePresenter : BasePresenter<ProfileView> , MomentItemActionDelegate {
    var mAuthModel:AuthenticationModel
    var mUserModel:UserModel
    var mMomentModel: MomentModel

    fun onTapEditProfileButton()

    fun onTapOpenCameraButton()

    fun onTapQrCodeImage()

    fun onTapGalleryImage()

    fun getUserId(): String

    fun updateUserInformation(user:UserVO)

    fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO)

    fun createMoment(moment: MomentVO)

    fun getMomentsFromUserBookmarked(currentUserId: String)

    fun deleteMomentFromUserBookmarked(currentUserId: String, momentId: String)
}