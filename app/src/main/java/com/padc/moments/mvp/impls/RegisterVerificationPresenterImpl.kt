package com.padc.moments.mvp.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.moments.data.models.ChatModel
import com.padc.moments.data.models.ChatModelImpl
import com.padc.moments.mvp.interfaces.RegisterVerificationPresenter
import com.padc.moments.mvp.views.RegisterVerificationView

class RegisterVerificationPresenterImpl : RegisterVerificationPresenter , ViewModel() {

    private var mView:RegisterVerificationView? = null
    private var mChatModel:ChatModel = ChatModelImpl

    override fun initPresenter(view: RegisterVerificationView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mChatModel.getOtp(
            onSuccess = {
                mView?.showOtp(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapVerifyButton() {
        mView?.navigateToRegisterScreen()
    }
}