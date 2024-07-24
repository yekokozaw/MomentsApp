package com.padc.moments.network.storage

import android.graphics.Bitmap
import android.icu.text.CaseMap.Title
import android.net.Uri
import com.padc.moments.data.vos.BookVo
import com.padc.moments.data.vos.CommentVO
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.data.vos.UserVO
import java.io.File

interface CloudFireStoreFirebaseApi {

    fun addUser(user: UserVO)

    fun addUserToGroup(
        userId: String,
        grade: String,
        token: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun deleteUserFromGroup(userId : String,grade: String)

    fun updateFCMToken(
        userId: String,
        token: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO)

    fun getUsers(
        onSuccess: (users: List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun downloadImage(
        imagePath : String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
        )

    fun getSpecificUser(
        userId: String,
        onSuccess: (users: UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun createMoment(moment: MomentVO, grade: String)

    fun deleteMoment(
        momentId: String,
        grade: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun updateAndUploadMomentImage(bitmap: Bitmap,onSuccess: (String) -> Unit,onFailure: (String) -> Unit)

    fun getMomentImages(): String

    fun clearMomentImages()
    fun getMoments(
        momentType: String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSingleMoment(
        momentType: String,
        momentId: String,
        onSuccess: (moment : MomentVO) -> Unit,
        onFailure: (String) -> Unit
    )
    fun createContact(scannerId: String, qrExporterId: String, contact: UserVO)

    fun getContacts(
        scannerId: String,
        onSuccess: (users: List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun getTokenByGroup(
        group: String,
        onSuccess: (tokens: List<String>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addLikedToMoment(momentId: String, likes: Map<String, String>, grade: String)

    fun getCommentFromMoment(
        momentId: String,
        momentType: String,
        onSuccess: (comments: List<CommentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addCommentToMoment(
        momentId: String,
        comment: CommentVO,
        momentType: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun updateCommentToMoment(
        momentId: String,
        momentType: String,
        commentSize : Int
    )

    fun addMomentToUserBookmarked(currentUserId: String, moment: MomentVO)

    fun deleteMomentFromUserBookmarked(currentUserId: String, momentId: String)

    fun getMomentsFromUserBookmarked(
        currentUserId: String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun uploadPdfFile(
        id : String,
        title: String,
        fileUri : Uri,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPdfBooks(
        onSuccess: (books : List<BookVo>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun deleteBook(
        bookId : String,
        pdfFilePath : String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )
}