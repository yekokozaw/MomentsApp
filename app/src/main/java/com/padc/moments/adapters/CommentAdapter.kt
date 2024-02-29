package com.padc.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.data.vos.CommentVO
import com.padc.moments.databinding.ViewHolderCommentListBinding
import com.padc.moments.views.viewholders.CommentViewHolder

class CommentAdapter : RecyclerView.Adapter<CommentViewHolder>() {

    private var mComments : List<CommentVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ViewHolderCommentListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mComments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindData(mComments[position])
    }

    @SuppressLint("NotifyDataSetChange")
    fun setNewData(comments : List<CommentVO>){
        mComments = comments
        notifyDataSetChanged()
    }
}