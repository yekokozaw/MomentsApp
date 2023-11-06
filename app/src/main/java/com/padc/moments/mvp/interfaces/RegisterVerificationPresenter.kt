package com.padc.moments.mvp.interfaces

import com.padc.moments.mvp.views.RegisterVerificationView

interface RegisterVerificationPresenter : BasePresenter<RegisterVerificationView> {
    fun onTapBackButton()
    fun onTapVerifyButton()
}