package com.padc.moments.data.models

import android.graphics.Bitmap
import com.padc.moments.data.vos.CommentVO
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.network.storage.CloudFireStoreFirebaseApi

interface MomentModel {
    var mFirebaseApi: CloudFireStoreFirebaseApi

    fun createMoment(moment: MomentVO,grade : String)

    fun deleteMoment(
        momentId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )
    fun updateAndUploadMomentImage(bitmap: Bitmap)

    fun addCommentToMoment(momentId : String,comment : CommentVO,onSuccess: (String) -> Unit,onFailure: (String) -> Unit)

    fun getCommentFromMoment(momentId : String,onSuccess: (comments :List<CommentVO>)-> Unit,onFailure: (String) -> Unit)
    fun getMomentImages(): String

    fun clearMomentImages()

    fun getMoments(
        type : String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addLikedToMoment(momentId: String,likes : Map<String, String>)

    fun addMomentToUserBookmarked(currentUserId:String,moment: MomentVO)
    fun deleteMomentFromUserBookmarked(currentUserId: String,momentId:String)

    fun getMomentsFromUserBookmarked(
        currentUserId: String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    )
}