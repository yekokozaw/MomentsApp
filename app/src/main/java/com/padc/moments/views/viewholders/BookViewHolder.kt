package com.padc.moments.views.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.moments.data.vos.BookVo
import com.padc.moments.databinding.ViewHolderBookBinding
import com.padc.moments.delegates.BookViewHolderDelegate

class BookViewHolder(private val binding : ViewHolderBookBinding,private val delegate: BookViewHolderDelegate)
    : RecyclerView.ViewHolder(binding.root) {

    private var mBook : BookVo? = null

    init {
        binding.btnDownload.setOnClickListener {
            mBook?.let { delegate.onTapPdfViewHolder(mBook!!.bookTitle, mBook!!.fileUrl) }
        }

        binding.root.setOnLongClickListener {
            mBook?.let { delegate.onLongPressViewHolder(mBook!!.bookId,mBook!!.bookTitle,mBook!!.fileUrl)
                true
            } == true
        }
    }

    fun bindData(book : BookVo){
        mBook = book
        binding.tvTitle.text = book.bookTitle

    }
}