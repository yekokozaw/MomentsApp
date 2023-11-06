package com.padc.moments.delegates

interface MomentItemActionDelegate {
    fun onTapBookmarkButton(id:String,isBookmarked:Boolean)
    fun onTapOptionButton(momentId:String,momentOwnerUserId:String)
}