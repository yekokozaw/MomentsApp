package com.padc.moments.mvp.interfaces

import com.padc.moments.mvp.views.LoginView

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapBackButton()
    fun onTapLoginButton(phoneNumber: String, email: String, password: String)

}