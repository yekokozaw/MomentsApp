package com.padc.moments.mvp.interfaces

import com.padc.moments.mvp.views.LoginView

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapBackButton()
    fun onTapLoginButton( fcmToken : String,phoneNumber: String, email: String, password: String)

}