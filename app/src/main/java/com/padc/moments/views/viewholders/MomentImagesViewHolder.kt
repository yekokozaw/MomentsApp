package com.padc.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.moments.R
import com.padc.moments.databinding.ViewHolderMomentImagesListBinding
import com.padc.moments.delegates.MomentItemActionDelegate

class MomentImagesViewHolder(itemView: View,private val delegate: MomentItemActionDelegate)
    : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderMomentImagesListBinding
    private var mImage : String = ""

    init {
        binding = ViewHolderMomentImagesListBinding.bind(itemView)
        setUpListeners()
    }

    private fun setUpListeners(){
        binding.ivMomentPictureMoment.setOnLongClickListener {
            delegate.onLongClickImage(mImage)
            true
        }
    }
    fun bindNewData(image: String,itemCount:Int) {
        mImage = image
        Glide.with(itemView.context)
            .load(image)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .override(700,900)
            .into(binding.ivMomentPictureMoment)

        val currentCount = adapterPosition + 1
        val imageCount = "$currentCount/${itemCount}"
        binding.tvImageCount.text = imageCount
    }
}