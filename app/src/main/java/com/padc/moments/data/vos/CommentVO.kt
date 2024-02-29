package com.padc.moments.data.vos

import java.sql.Timestamp

data class CommentVO(
    var userName : String = "",

    var timestamp: Long = 0L,

    var userProfileImage:String = "",

    var comment : String = ""
)
