package com.padc.moments.network.auth

import com.padc.moments.data.vos.UserVO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.padc.moments.network.storage.PresenceManager

object FirebaseAuthManager : AuthManager {

    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var mPresenceManager: PresenceManager

    override fun login(
        phoneNumber: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                onSuccess()
            } else {
                onFailure(it.exception?.message ?: "Check Internet Connection")
            }
        }
    }

    override fun register(
        userName: String,
        phoneNumber: String,
        email: String,
        password: String,
        birthDate: String,
        gender: String,
        imageUrl:String,
        fcmKey:String,
        onSuccess: (user:UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {

        mPresenceManager = PresenceManager(getUserId())
        mPresenceManager.addUserId(false)

        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                val user = mFirebaseAuth.currentUser
                user?.sendEmailVerification()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            mFirebaseAuth.currentUser?.updateProfile(
                                UserProfileChangeRequest.Builder().setDisplayName(userName).build()
                            )
                            onSuccess(UserVO(getUserId(),userName,phoneNumber,email,
                                password, birthDate, gender, getUserId(),imageUrl,fcmKey))
                        }
                        else onFailure(it.exception?.message ?: "Something wrong!")
                    }

            } else {
                onFailure(it.exception?.message ?: "Check Internet Connection")
            }
        }
    }

    override fun getUserId(): String {
        return mFirebaseAuth.currentUser?.uid.toString()
    }
}