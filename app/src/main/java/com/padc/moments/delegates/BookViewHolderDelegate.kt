package com.padc.moments.delegates

interface  BookViewHolderDelegate {
    fun onTapPdfViewHolder(title : String,fileUrl : String)
    fun onLongPressViewHolder(id : String,title: String,fileUrl: String)
}