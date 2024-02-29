package com.padc.moments.views.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.moments.data.vos.CommentVO
import com.padc.moments.databinding.ViewHolderCommentListBinding
import com.padc.moments.utils.getTimeAgo

class CommentViewHolder(
    private val binding : ViewHolderCommentListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(comment : CommentVO){
        binding.tvUserName.text = comment.userName
        binding.tvUserComment.text = comment.comment
        Glide.with(binding.root.context)
            .load(comment.userProfileImage)
            .into(binding.ivUserProfile)
        binding.tvTimeStamp.text = getTimeAgo(comment.timestamp)
    }

}