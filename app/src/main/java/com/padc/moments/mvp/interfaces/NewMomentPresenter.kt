package com.padc.moments.mvp.interfaces

import android.graphics.Bitmap
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.delegates.NewMomentImageDelegate
import com.padc.moments.mvp.views.NewMomentView

interface NewMomentPresenter : BasePresenter<NewMomentView> , NewMomentImageDelegate {

    var mAuthModel: AuthenticationModel
    var mMomentModel: MomentModel
    var mUserModel: UserModel
    fun onTapBackButton()

    fun onTapCreateButton(moment: MomentVO,title: String,body: String)

    fun createMomentImages(bitmap: Bitmap)

    fun getMomentImages(): String

    fun clearMomentImages()

    fun getTokenByGroup(group : String)
    fun getUserId(): String
}