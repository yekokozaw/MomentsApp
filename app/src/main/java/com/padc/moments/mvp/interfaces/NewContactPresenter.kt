package com.padc.moments.mvp.interfaces

import com.padc.moments.data.vos.UserVO
import com.padc.moments.mvp.views.NewContactView

interface NewContactPresenter : BasePresenter<NewContactView> {
    fun getUsers(qrExporterUserId:String)
    fun createContact(scannerId:String,qrExporterId:String,contact: UserVO)
    fun getScannerUserId(): String
}