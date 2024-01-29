package com.padc.moments.network.storage

import android.graphics.Bitmap
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.data.vos.UserVO

interface CloudFireStoreFirebaseApi {
    fun addUser(user: UserVO)

    fun updateFCMToken( userId: String,token : String)
    fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO)
    fun getUsers(
        onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit
    )

    fun getSpecificUser(userId: String,onSuccess: (users: UserVO) -> Unit, onFailure: (String) -> Unit)

    fun createMoment(moment: MomentVO)

    fun deleteMoment(
        momentId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )
    fun updateAndUploadMomentImage(bitmap: Bitmap)

    fun getMomentImages(): String

    fun clearMomentImages()
    fun getMoments(
        onSuccess: (moments: List<MomentVO>) -> Unit, onFailure: (String) -> Unit
    )

    fun createContact(scannerId: String, qrExporterId: String, contact: UserVO)

    fun getContacts(
        scannerId: String,
        onSuccess: (users: List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addLikedToMoment(momentId: String,likes : Map<String, String>)

    fun deleteLikedToMoment(momentId: String,currentUserId: String,name: String)
    fun addMomentToUserBookmarked(currentUserId: String, moment: MomentVO)

    fun deleteMomentFromUserBookmarked(currentUserId: String, momentId: String)

    fun getMomentsFromUserBookmarked(
        currentUserId: String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    )
}