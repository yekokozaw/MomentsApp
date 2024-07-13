package com.padc.moments.mvp.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.ChatModel
import com.padc.moments.data.models.ChatModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.mvp.interfaces.ContactsPresenter
import com.padc.moments.mvp.views.ContactsView

class ContactsPresenterImpl : ContactsPresenter , ViewModel() {

    private var mView:ContactsView? = null
    private var mUserModel:UserModel = UserModelImpl
    private var mAuthModel:AuthenticationModel = AuthenticationModelImpl
    private var mChatModel:ChatModel = ChatModelImpl

    override fun initPresenter(view: ContactsView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mChatModel.getGroups(
            onSuccess = {
                mView?.getGroupList(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapGroupItem(groupId: Long) {
        mView?.navigateToChatDetailScreenFromGroupItem(groupId.toString())
    }

    override fun onTapAddNewContactButton() {
        mView?.navigateToNewContactScreen()
    }

    override fun getContacts(scannerId:String) {
        mUserModel.getContacts(
            scannerId,
            onSuccess = {
                mView?.showContacts(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun getUserId(): String {
        return mAuthModel.getUserIdFromDb()
    }

    override fun onTapAlphabetItem(position: Int) {}

    override fun onTapChatItem(userId: String) {
        mView?.navigateToChatDetailScreen(userId)
    }

    override fun onTapCheckbox(userId: String, isCheck: Boolean) {}


}