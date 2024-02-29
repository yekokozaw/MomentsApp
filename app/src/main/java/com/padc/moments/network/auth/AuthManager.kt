package com.padc.moments.network.auth

import com.padc.moments.data.vos.UserVO

interface AuthManager {

    fun login(phoneNumber: String,email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun register(
        userName:String,
        phoneNumber:String,
        email: String,
        password: String,
        birthDate:String,
        gender:String,
        imageUrl:String,
        fcmKey:String,
        grade : String,
        onSuccess: (user: UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getUserId() : String
}