package com.padc.moments.network.storage

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.data.vos.UserVO
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.padc.moments.data.vos.CommentVO
import java.io.ByteArrayOutputStream
import java.util.UUID

@SuppressLint("StaticFieldLeak")
object CloudFireStoreFirebaseApiImpl : CloudFireStoreFirebaseApi {

    private var database: FirebaseFirestore = Firebase.firestore
    private val storageRef = FirebaseStorage.getInstance().reference

    // General
    private var mMomentImages: String = ""
    override fun addUser(user: UserVO) {

        val userMap = hashMapOf(
            "id" to user.userId,
            "name" to user.userName,
            "phone_number" to user.phoneNumber,
            "email" to user.email,
            "password" to user.password,
            "birth_date" to user.birthDate,
            "gender" to user.gender,
            "qr_code" to user.userId,
            "image_url" to user.imageUrl,
            "fcm_key" to user.fcmKey,
            "grade" to user.grade
        )

        database.collection("users")
            .document(user.userId)
            .set(userMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed Added")
            }
    }

    override fun addUserToGroup(userId: String,grade: String, token: String) {
        val hashMap = hashMapOf("token" to token)
        database.collection(grade)
            .document(userId)
            .set(hashMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added token")
            }
    }

    override fun updateFCMToken(userId: String, token: String) {

        database.collection("users")
            .document(userId)
            .update("fcm_key",token)
    }

    private fun changeBitmapToUrlString(bitmap: Bitmap): Task<Uri> {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageRef.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)

        //Log.i("ImageURL", uploadTask.toString())

        uploadTask.addOnFailureListener {
            //Log.i("FileUpload", "File uploaded failed")
        }.addOnSuccessListener {
            //Log.i("FileUpload", "File uploaded successful")
        }

        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }
        return urlTask
    }

    override fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO) {

        val urlTask = changeBitmapToUrlString(bitmap)

        urlTask.addOnCompleteListener {
            val imageUrl = it.result?.toString()
            Log.i("ImageURL", imageUrl.toString())
            addUser(
                UserVO(
                    userId = user.userId,
                    userName = user.userName,
                    phoneNumber = user.phoneNumber,
                    email = user.email,
                    password = user.password,
                    birthDate = user.birthDate,
                    gender = user.gender,
                    qrCode = user.userId,
                    imageUrl = imageUrl ?: "",
                    fcmKey = user.fcmKey
                )
            )
        }
    }

    override fun getUsers(onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit) {
        database.collection("users")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val userList: MutableList<UserVO> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for (document in documentList) {
                        val data = document.data
                        val id = data?.get("id") as String
                        val name = data["name"] as String
                        val phoneNumber = data["phone_number"] as String
                        val email = data["email"] as String
                        val password = data["password"] as String
                        val birthDate = data["birth_date"] as String
                        val gender = data["gender"] as String
                        val qrCode = data["qr_code"] as String
                        val imageUrl = data["image_url"] as String
                        val fcmKey = data["fcm_key"].toString()
                        val grade = data["grade"].toString()
                        val user = UserVO(
                            id,
                            name,
                            phoneNumber,
                            email,
                            password,
                            birthDate,
                            gender,
                            qrCode,
                            imageUrl,
                            fcmKey,
                            grade
                        )
                        userList.add(user)
                    }
                    onSuccess(userList)
                }
            }
    }


    override fun getSpecificUser(
        userId: String,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("users")
            .document(userId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    //val userList: MutableList<UserVO> = arrayListOf()
                    val data = value?.data
                    val id = data?.get("id") as String
                    val name = data["name"] as String
                    val phoneNumber = data["phone_number"] as String
                    val email = data["email"] as String
                    val password = data["password"] as String
                    val birthDate = data["birth_date"] as String
                    val gender = data["gender"] as String
                    val qrCode = data["qr_code"] as String
                    val imageUrl = data["image_url"] as String
                    val fcmKey = data["fcm_key"].toString()
                    val grade = data["grade"] as String
                    val user = UserVO(
                        id,
                        name,
                        phoneNumber,
                        email,
                        password,
                        birthDate,
                        gender,
                        qrCode,
                        imageUrl,
                        fcmKey,
                        grade = grade
                    )
                    onSuccess(user)
                }
            }
    }

    override fun createMoment(moment: MomentVO,grade : String) {
        val userMap = hashMapOf(
            "id" to moment.id,
            "user_id" to moment.userId,
            "user_name" to moment.userName,
            "likes" to moment.likes,
            "user_profile_image" to moment.userProfileImage,
            "caption" to moment.caption,
            "image_url" to moment.imageUrl
        )

        database.collection(grade)
            .document(moment.id)
            .set(userMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed Added")
            }
    }

    override fun deleteMoment(
        momentId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("moments")
            .document(momentId)
            .delete()
            .addOnSuccessListener {
                onSuccess("Deleted successfully")
            }.addOnFailureListener {
                onFailure("Deleted failed")
            }
    }

    override fun updateAndUploadMomentImage(bitmap: Bitmap) {
        val urlTask = changeBitmapToUrlString(bitmap)

        urlTask.addOnCompleteListener {
            val imageUrl = it.result?.toString()
            mMomentImages += "$imageUrl,"
        }
    }

    override fun getMomentImages(): String {
        return mMomentImages
    }

    override fun clearMomentImages() {
        mMomentImages = ""
    }

    override fun getMoments(
        momentType : String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection(momentType)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val momentList: MutableList<MomentVO> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for (document in documentList) {
                        val data = document.data
                        val id = data?.get("id") as String
                        val userId = data["user_id"] as? String ?: ""
                        val userName = data["user_name"] as String
                        val userProfileImage = data["user_profile_image"] as String
                        val caption = data["caption"] as String
                        val imageUrl = data["image_url"] as String
                        val likes = data["likes"] as String
                        val likedList = data["Liked"] as? Map<String, String> ?: emptyMap()
                        val isBookmarked = data["is_bookmarked"] as? Boolean ?: false
                        val moment = MomentVO(
                            id,
                            userId,
                            userName,
                            userProfileImage,
                            caption,
                            imageUrl,
                            likedList,
                            likes,
                            isBookmarked
                        )
                        momentList.add(moment)
                    }
                    onSuccess(momentList)
                }
            }
    }

    override fun createContact(scannerId: String, qrExporterId: String, contact: UserVO) {
        val userMap = hashMapOf(
            "id" to contact.userId,
            "name" to contact.userName,
            "phone_number" to contact.phoneNumber,
            "email" to contact.email,
            "password" to contact.password,
            "birth_date" to contact.birthDate,
            "gender" to contact.gender,
            "qr_code" to contact.userId,
            "image_url" to contact.imageUrl,
            "fcm_key" to contact.fcmKey
        )

        database.collection("users")
            .document(scannerId)
            .collection("contacts")
            .document(qrExporterId)
            .set(userMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed Added")
            }
    }

    override fun getContacts(
        scannerId: String,
        onSuccess: (users: List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("users")
            .document(scannerId)
            .collection("contacts")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val userList: MutableList<UserVO> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for (document in documentList) {
                        val data = document.data
                        val id = data?.get("id") as String
                        val name = data["name"] as String
                        val phoneNumber = data["phone_number"] as String
                        val email = data["email"] as String
                        val password = data["password"] as String
                        val birthDate = data["birth_date"] as String
                        val gender = data["gender"] as String
                        val qrCode = data["qr_code"] as String
                        val imageUrl = data["image_url"] as String
                        val fcmKey = data["fcm_key"].toString()
                        val user = UserVO(
                            id,
                            name,
                            phoneNumber,
                            email,
                            password,
                            birthDate,
                            gender,
                            qrCode,
                            imageUrl,
                            fcmKey
                        )
                        userList.add(user)
                    }
                    onSuccess(userList)
                }
            }
    }

    override fun getTokenByGroup(
        group: String,
        onSuccess: (tokens: List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection(group)
            .addSnapshotListener{value,error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet")
                } ?: run {
                    val tokenList : MutableList<String> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for(document in documentList){
                        val token = document.data?.get("token") as String
                        tokenList.add(token)
                    }
                    onSuccess(tokenList)
                }
            }
    }

    override fun addLikedToMoment(momentId: String,likes : Map<String, String>) {
        database.collection("moments")
            .document(momentId)
    .update("Liked",likes)
    .addOnSuccessListener {
        Log.d("success","Successful Add")
    }.addOnFailureListener {
        Log.d("addLike","Failure")
    }
}

    override fun getCommentFromMoment(
        momentId: String,
        onSuccess: (comments :List<CommentVO>)-> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("moments")
            .document(momentId)
            .collection("Comments")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val commentList : MutableList<CommentVO> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for (document in documentList){
                        val data = document.data
                        val userName = data?.get("userName") as String
                        val userProfile = data["userProfileImage"] as String
                        val comment = data["comment"] as String
                        val timeStamp = data["timestamp"] as Long
                        val commentData =  CommentVO(
                            userName,
                            timeStamp,
                            userProfile,
                            comment
                        )
                        commentList.add(commentData)
                    }
                    onSuccess(commentList)
                }
            }
    }

    override fun addCommentToMoment(momentId: String, comment: CommentVO, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        database.collection("moments")
            .document(momentId)
            .collection("Comments")
            .document(comment.timestamp.toString())
            .set(comment)
            .addOnSuccessListener {
                onSuccess("Done")
            }
            .addOnFailureListener {
                onFailure("Failure")
            }
    }

    override fun addMomentToUserBookmarked(currentUserId: String, moment: MomentVO) {
        val userMap = hashMapOf(
            "id" to moment.id,
            "Liked" to moment.likedList,
            "user_id" to moment.userId,
            "user_name" to moment.userName,
            "user_profile_image" to moment.userProfileImage,
            "caption" to moment.caption,
            "image_url" to moment.imageUrl
        )

        database.collection("users")
            .document(currentUserId)
            .collection("moments")
            .document(moment.id)
            .set(userMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed Added")
            }
    }

    override fun deleteMomentFromUserBookmarked(currentUserId: String, momentId: String) {
        database.collection("users")
            .document(currentUserId)
            .collection("moments")
            .document(momentId)
            .delete()
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully deleted")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed deleted")
            }
    }

    override fun getMomentsFromUserBookmarked(
        currentUserId: String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("users")
            .document(currentUserId)
            .collection("moments")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val momentList: MutableList<MomentVO> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for (document in documentList) {
                        val data = document.data
                        val id = data?.get("id") as String
                        val userId = data["user_id"] as? String ?: ""
                        val userName = data["user_name"] as String
                        val userProfileImage = data["user_profile_image"] as String
                        val caption = data["caption"] as String
                        val likes = data["likes"] as? String ?: ""
                        val imageUrl = data["image_url"] as String
                        val likedList = data["Liked"] as? Map<String, String> ?: emptyMap()
                        val isBookmarked = data["is_bookmarked"] as? Boolean ?: false
                        val moment = MomentVO(
                            id,
                            userId,
                            userName,
                            userProfileImage,
                            caption,
                            imageUrl,
                            likedList,
                            likes,
                            isBookmarked
                        )
                        momentList.add(moment)
                    }
                    onSuccess(momentList)
                }
            }
    }
}