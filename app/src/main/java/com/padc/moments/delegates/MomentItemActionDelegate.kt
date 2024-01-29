package com.padc.moments.delegates

interface MomentItemActionDelegate {
    fun onTapBookmarkButton(id:String,isBookmarked:Boolean)
    fun onTapOptionButton(momentId:String,momentOwnerUserId:String)

    fun onTapLikeButton(momentId : String,likes : Map<String,String>,isLike : Boolean)
}