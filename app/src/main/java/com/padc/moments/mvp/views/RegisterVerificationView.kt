package com.padc.moments.mvp.views

interface RegisterVerificationView : BaseView {
    fun navigateToPreviousScreen()
    fun navigateToRegisterScreen()
    fun showOtp(otp: String)
}