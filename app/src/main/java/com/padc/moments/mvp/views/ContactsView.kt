package com.padc.moments.mvp.views

import com.padc.moments.data.vos.GroupVO
import com.padc.moments.data.vos.UserVO

interface ContactsView  : BaseView {
    fun navigateToNewContactScreen()
    fun showContacts(contactList: List<UserVO>)
    fun navigateToChatDetailScreen(userId: String)
    fun addUserToGroup(userId: String)
    fun getGroupList(groupList: List<GroupVO>)
    fun navigateToChatDetailScreenFromGroupItem(groupId: String)
}