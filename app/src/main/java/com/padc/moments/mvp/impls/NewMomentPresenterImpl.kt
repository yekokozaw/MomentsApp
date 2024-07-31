package com.padc.moments.mvp.impls

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.MomentModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.data.vos.fcm.Data
import com.padc.moments.data.vos.fcm.DataX
import com.padc.moments.data.vos.fcm.FCMBody
import com.padc.moments.mvp.interfaces.NewMomentPresenter
import com.padc.moments.mvp.views.NewMomentView
import com.padc.moments.network.storage.sendMessageToTopic

class NewMomentPresenterImpl: NewMomentPresenter , ViewModel() {

    private var mView:NewMomentView? = null
    private var tokens : List<String> = listOf()
    private var momentType : String = ""
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

    override fun getMomentType(type: String) {
        momentType = type
    }

    override fun onTapCreateButton(moment:MomentVO,title: String,body: String,grade : String) {
        val dataFCM = Data(
            title = title,
            body = body,
            chat_type = "",
            chat_id = "",
            data = DataX()
        )
        val fcmBody = FCMBody(tokens.toList().distinct(),dataFCM)
        mMomentModel.createMoment(moment, grade = momentType)
        if (grade == "all"){
            //sendMessageToTopic("all",title,body)
        }else{
            mUserModel.sendFCMNotification(
                fcmBody,
                onSuccess = {
                    mView?.finishFragment()
                }, onFailure = {
                    mView?.showError(it)
                })
        }
    }

    override fun createMomentImages(bitmap: Bitmap) {
        mMomentModel.updateAndUploadMomentImage(
            bitmap,
            onSuccess = {
                mView?.imageReady(true)
            }, onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun getMomentImages() : String {
        return mMomentModel.getMomentImages()
    }

    override fun clearMomentImages() {
        mMomentModel.clearMomentImages()
    }

    override fun getTokenByGroup(
        group: String
    ) {
        mUserModel.getTokenByGroup(group,
            onSuccess = {
                tokens = it
            }, onFailure = {
                mView?.showError(it)
            })
    }

    override fun getUserId(): String {
        return mAuthModel.getUserIdFromDb()
    }
}