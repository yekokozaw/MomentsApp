package com.padc.moments.mvp.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.data.vos.UserVO
import com.padc.moments.mvp.interfaces.NewContactPresenter
import com.padc.moments.mvp.views.NewContactView

class NewContactPresenterImpl : NewContactPresenter , ViewModel() {

    private var mView:NewContactView? = null
    private val mUserModel:UserModel = UserModelImpl
    private val mAuthModel:AuthenticationModel = AuthenticationModelImpl

    override fun initPresenter(view: NewContactView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {

    }

    override fun getUsers(qrExporterUserId:String) {
        mUserModel.getUsers(
            onSuccess = {
                mView?.getUsers(it,qrExporterUserId)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun createContact(scannerId:String,qrExporterId:String,contact: UserVO) {
        mUserModel.createContact(scannerId, qrExporterId, contact)
    }

    override fun getScannerUserId(): String {
        return mAuthModel.getUserIdFromDb()
    }
}