package com.padc.moments.data.models

import android.graphics.Bitmap
import android.net.Uri
import com.padc.moments.data.vos.BookVo
import com.padc.moments.data.vos.CommentVO
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.network.storage.CloudFireStoreFirebaseApiImpl
import com.padc.moments.network.storage.CloudFireStoreFirebaseApi
import java.io.File

object MomentModelImpl : MomentModel {

    override var mFirebaseApi: CloudFireStoreFirebaseApi = CloudFireStoreFirebaseApiImpl

    override fun createMoment(moment: MomentVO,grade : String) {
        mFirebaseApi.createMoment(moment,grade)
    }

    override fun deleteMoment(
        momentId: String,
        grade: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.deleteMoment(momentId,grade, onSuccess, onFailure)
    }

    override fun updateAndUploadMomentImage(bitmap: Bitmap,onSuccess: (String) -> Unit,onFailure: (String) -> Unit) {
        mFirebaseApi.updateAndUploadMomentImage(bitmap,onSuccess,onFailure)
    }

    override fun addCommentToMoment(
        momentId: String,
        comment: CommentVO,
        momentType : String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.addCommentToMoment(momentId, comment,momentType, onSuccess, onFailure)
    }

    override fun updateCommentToMoment(momentId: String, momentType: String,commentSize : Int) {
        mFirebaseApi.updateCommentToMoment(momentId,momentType,commentSize)
    }

    override fun getCommentFromMoment(
        momentId: String,
        momentType: String,
        onSuccess: (comments: List<CommentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getCommentFromMoment(momentId,momentType,onSuccess,onFailure)
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

    override fun getSingleMoment(
        momentType: String,
        momentId: String,
        onSuccess: (moment: MomentVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getSingleMoment(momentType,momentId,onSuccess,onFailure)
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

    override fun getPdfBooks(
        onSuccess: (books: List<BookVo>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getPdfBooks(onSuccess,onFailure)
    }

    override fun uploadPdfFile(
        id: String,
        title: String,
        fileUri: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadPdfFile(
            id = id,
            title,
            fileUri = fileUri,
            onSuccess,
            onFailure
        )
    }

    override fun deleteBook(
        bookId: String,
        pdfFilePath: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.deleteBook(
            bookId,
            pdfFilePath,
            onSuccess,
            onFailure
        )
    }
}