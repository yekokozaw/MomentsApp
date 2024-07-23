package com.padc.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.data.vos.BookVo
import com.padc.moments.databinding.ViewHolderBookBinding
import com.padc.moments.delegates.BookViewHolderDelegate
import com.padc.moments.views.viewholders.BookViewHolder

class BookAdapter (private val delegate : BookViewHolderDelegate): RecyclerView.Adapter<BookViewHolder>() {
    private var mBooks : List<BookVo> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ViewHolderBookBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(books : List<BookVo>){
        mBooks = books
        notifyDataSetChanged()
    }
}