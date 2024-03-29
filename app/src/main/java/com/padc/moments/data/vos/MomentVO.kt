package com.padc.moments.data.vos

data class MomentVO(
    var id:String = "",
    var userId:String = "",
    var userName:String = "",
    var userProfileImage:String = "",
    var caption:String = "",
    var imageUrl:String = "",
    var likedList: Map<String, String>?,
    var likes:String = "",
    var isLiked: Boolean = false,
    var isBookmarked:Boolean = false,
    var commentCount : Int = 0
)
