package com.padc.moments.data.models

import android.graphics.Bitmap
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.network.storage.CloudFireStoreFirebaseApiImpl
import com.padc.moments.network.storage.CloudFireStoreFirebaseApi

object MomentModelImpl : MomentModel {

    override var mFirebaseApi: CloudFireStoreFirebaseApi = CloudFireStoreFirebaseApiImpl

    override fun createMoment(moment: MomentVO) {
        mFirebaseApi.createMoment(moment)
    }

    override fun deleteMoment(
        momentId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.deleteMoment(momentId, onSuccess, onFailure)
    }

    override fun updateAndUploadMomentImage(bitmap: Bitmap) {
        mFirebaseApi.updateAndUploadMomentImage(bitmap)
    }

    override fun getMomentImages(): String {
        return mFirebaseApi.getMomentImages()
    }

    override fun clearMomentImages() {
        mFirebaseApi.clearMomentImages()
    }

    override fun getMoments(
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getMoments(onSuccess = onSuccess, onFailure = onFailure)
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