package com.padc.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.moments.R
import com.padc.moments.databinding.ViewHolderMomentImagesListBinding

class MomentImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderMomentImagesListBinding

    init {
        binding = ViewHolderMomentImagesListBinding.bind(itemView)
    }

    fun bindNewData(image: String,itemCount:Int) {
        Glide.with(itemView.context)
            .load(image)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .override(800,800)
            .into(binding.ivMomentPictureMoment)

        val currentCount = adapterPosition + 1
        binding.tvImageCount.text = "$currentCount/${itemCount}"
    }
}