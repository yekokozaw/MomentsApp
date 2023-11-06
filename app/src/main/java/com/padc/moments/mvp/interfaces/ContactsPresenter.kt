package com.padc.moments.mvp.interfaces

import com.padc.moments.delegates.AlphabetActionItemDelegate
import com.padc.moments.delegates.ChatItemActionDelegate
import com.padc.moments.delegates.GroupItemActionDelegate
import com.padc.moments.mvp.views.ContactsView

interface ContactsPresenter  : BasePresenter<ContactsView>,GroupItemActionDelegate,AlphabetActionItemDelegate,ChatItemActionDelegate {
    fun onTapAddNewContactButton()

    fun getContacts(scannerId:String)

    fun getUserId() :String
}