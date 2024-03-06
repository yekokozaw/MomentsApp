package com.padc.moments.data.models

import android.graphics.Bitmap
import com.padc.moments.data.vos.CommentVO
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.network.storage.CloudFireStoreFirebaseApiImpl
import com.padc.moments.network.storage.CloudFireStoreFirebaseApi
import com.padc.moments.network.storage.sendMessageToTopic

object MomentModelImpl : MomentModel {

    override var mFirebaseApi: CloudFireStoreFirebaseApi = CloudFireStoreFirebaseApiImpl

    override fun createMoment(moment: MomentVO,grade : String) {
        mFirebaseApi.createMoment(moment,grade)
        sendMessageToTopic("all",moment.userName,moment.caption)
    }

    override fun deleteMoment(
        momentId: String,
        grade: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.deleteMoment(momentId,grade, onSuccess, onFailure)
    }

    override fun updateAndUploadMomentImage(bitmap: Bitmap) {
        mFirebaseApi.updateAndUploadMomentImage(bitmap)
    }

    override fun addCommentToMoment(momentId: String, comment: CommentVO, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.addCommentToMoment(momentId, comment,onSuccess,onFailure)
    }

    override fun getCommentFromMoment(
        momentId: String,
        onSuccess: (comments: List<CommentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getCommentFromMoment(momentId,onSuccess,onFailure)
    }

    override fun getMomentImages(): String {
        return mFirebaseApi.getMomentImages()
    }

    override fun clearMomentImages() {
        mFirebaseApi.clearMomentImages()
    }

    override fun getMoments(
        type : String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getMoments(type,onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun addLikedToMoment(momentId: String,likes : Map<String, String>,grade: String) {
        mFirebaseApi.addLikedToMoment(momentId,likes,grade)
    }

    override fun addMomentToUserBookmarked(currentUserId: String, moment: MomentVO) {
        mFirebaseApi.addMomentToUserBookmarked(currentUserId,moment)
    }

    override fun deleteMomentFromUserBookmarked(currentUserId: String, momentId: String) {
        mFirebaseApi.deleteMomentFromUserBookmarked(currentUserId,momentId)
    }

    override fun getMomentsFromUserBookmarked(
        currentUserId: String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getMomentsFromUserBookmarked(currentUserId, onSuccess, onFailure)
    }
}