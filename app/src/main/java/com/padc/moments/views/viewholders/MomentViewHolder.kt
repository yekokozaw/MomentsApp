package com.padc.moments.views.viewholders

import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.moments.R
import com.padc.moments.adapters.MomentImagesAdapter
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.databinding.ViewHolderMomentListBinding
import com.padc.moments.delegates.MomentItemActionDelegate
import com.padc.moments.utils.hide
import com.padc.moments.utils.show

class MomentViewHolder(itemView: View,private val delegate: MomentItemActionDelegate) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderMomentListBinding

    // Adapters
    private lateinit var mAdapter: MomentImagesAdapter

    // General
    private var mMoment:MomentVO? = null

    init {
        binding = ViewHolderMomentListBinding.bind(itemView)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnMomentOption.setOnClickListener {
            delegate.onTapOptionButton(mMoment?.id ?: "",mMoment?.userId ?: "")
        }

        binding.ivPostComment.setOnClickListener {
            delegate.onTapCommentButton(mMoment?.id ?: "")
        }
        binding.btnPostFavourite.setOnClickListener {
            if(mMoment?.isLiked == true) {
                mMoment!!.likedList?.let { it1 -> delegate.onTapLikeButton(mMoment?.id ?: "" , it1,false) }
            } else {
                mMoment!!.likedList?.let { it1 -> delegate.onTapLikeButton(mMoment?.id ?: "" , it1,true) }
            }
        }

        binding.btnMomentBookmark.setOnClickListener {
            if(mMoment?.isBookmarked == true) {
                delegate.onTapBookmarkButton(mMoment?.id ?: "",false)
            } else {
                delegate.onTapBookmarkButton(mMoment?.id ?: "",true)
            }
        }
    }

    fun bindData(data: MomentVO, tabName: String) {
        mMoment = data
        binding.tvMomentLastOnline.text = getTimeAgo(data.id.toLong())
        when(val likes = data.likedList?.size.toString()){
            "0" -> binding.tvLikes.text = "0"
            "1" -> binding.tvLikes.text = "1 like"
            else -> binding.tvLikes.text = "$likes likes"
        }
        binding.tvMomentProfileName.text = data.userName
        binding.tvMomentCaption.text = data.caption
        binding.tvPostNumberOfComments.text = data.commentCount.toString()
        Glide.with(itemView.context)
            .load(data.userProfileImage)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.ivMomentProfilePic)

        //if we don't set els branch,the bug can occurred
        if (data.imageUrl.isEmpty())
            binding.viewPagerMomentImages.hide()
        else
            binding.viewPagerMomentImages.show()

        setUpMomentImages()
        mAdapter.setNewData(changeImageStringToList(data.imageUrl))

        if (data.isLiked){
            binding.btnPostFavourite.setImageResource(R.drawable.baseline_favorite_24dp)
            mMoment?.isLiked = true
        }else {
            binding.btnPostFavourite.setImageResource(R.drawable.baseline_favorite_border_accent_24dp)
            mMoment?.isLiked = false
        }

        if(data.isBookmarked) {
            binding.btnMomentBookmark.setImageResource(R.drawable.baseline_bookmark_red_24dp)
            mMoment?.isBookmarked = true
        } else {
            binding.btnMomentBookmark.setImageResource(R.drawable.baseline_bookmark_border_accent_24dp)
            mMoment?.isBookmarked = false
        }

        if(tabName == "profile") {
            binding.btnMomentBookmark.setImageResource(R.drawable.baseline_bookmark_red_24dp)
            mMoment?.isBookmarked = true
        }
    }

    private fun changeImageStringToList(imageString:String) : List<String> {
        return imageString.split(',').toList()
    }

    private fun setUpMomentImages() {
        mAdapter = MomentImagesAdapter(delegate)
        binding.viewPagerMomentImages.adapter = mAdapter
    }

    fun getTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val timeAgo = DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.MINUTE_IN_MILLIS)
        return timeAgo.toString()
    }
}