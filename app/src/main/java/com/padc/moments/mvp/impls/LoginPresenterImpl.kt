package com.padc.moments.mvp.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.mvp.interfaces.LoginPresenter
import com.padc.moments.mvp.views.LoginView

class LoginPresenterImpl : LoginPresenter, ViewModel() {

    private var mView: LoginView? = null
    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl

    override fun initPresenter(view: LoginView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapLoginButton(phoneNumber: String, email: String, password: String) {
        mAuthModel.login(
            phoneNumber,
            email,
            password,
            onSuccess = {
                mView?.navigateToHomeScreen()
            },
            onFailure = {
                mView?.showError(it)
            })
    }
}