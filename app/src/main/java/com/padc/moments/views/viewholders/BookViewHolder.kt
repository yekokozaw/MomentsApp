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

    }

    fun bindData(book : BookVo){
        mBook = book
        binding.tvTitle.text = book.bookTitle
        binding.tvGradeName.text = book.bookTitle

    }
}