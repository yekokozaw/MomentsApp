package com.padc.moments.mvp.views

import com.padc.moments.data.vos.GroupVO
import com.padc.moments.data.vos.PrivateMessageVO
import com.padc.moments.data.vos.UserVO

interface ChatView  : BaseView {
    fun showContacts(contactList: List<UserVO>)
    fun navigateToChatDetailScreen(userId: String)
    fun showUserId(userIdList: List<String>)
    fun getUsers(userList: List<UserVO>)
    fun getGroups(groupList: List<GroupVO>)
    fun navigateToGroupChatDetailScreen(groupId: Long)
    fun getLastMessage(message: PrivateMessageVO)
}