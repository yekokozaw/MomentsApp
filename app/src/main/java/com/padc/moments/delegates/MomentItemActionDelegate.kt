package com.padc.moments.delegates

interface MomentItemActionDelegate {
    fun onTapBookmarkButton(id:String,isBookmarked:Boolean)
    fun onTapOptionButton(momentId:String,momentOwnerUserId:String)

    fun onLongClickImage(imageUrl : String)
    fun onTapCommentButton(momentId: String)
    fun onTapLikeButton(momentId : String,likes : Map<String,String>,isLike : Boolean)
}