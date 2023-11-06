package com.padc.moments.mvp.interfaces

import android.graphics.Bitmap
import com.padc.moments.data.vos.UserVO
import com.padc.moments.mvp.views.RegisterView

interface RegisterPresenter : BasePresenter<RegisterView> {

    fun onTapBackButton()

    fun onTapOpenCameraButton()
    fun onTapSignUpButton(user: UserVO,bitmap: Bitmap)

    fun onTapProfileImage()

    fun onPhotoTaken(bitmap: Bitmap)
}