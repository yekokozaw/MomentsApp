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
        binding.tvLikes.text = data.likedList?.size.toString()
        binding.tvMomentProfileName.text = data.userName
        binding.tvMomentCaption.text = data.caption

        Glide.with(itemView.context)
            .load(data.userProfileImage)
            .into(binding.ivMomentProfilePic)

        if (data.imageUrl.isEmpty())
            binding.viewPagerMomentImages.visibility = View.GONE

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
        mAdapter = MomentImagesAdapter()
        binding.viewPagerMomentImages.adapter = mAdapter
    }

    fun getTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val timeAgo = DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.MINUTE_IN_MILLIS)
        return timeAgo.toString()
    }
}