package com.padc.moments.mvp.views

import com.padc.moments.data.vos.UserVO

interface NewContactView : BaseView {
    fun getUsers(userList: List<UserVO>,qrExporterUserId:String)
}