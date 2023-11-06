package com.padc.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.moments.data.vos.giphy.Data
import com.padc.moments.databinding.ViewHolderGifListBinding
import com.padc.moments.delegates.GifItemActionDelegate

class GifViewHolder(itemView: View,private val delegate: GifItemActionDelegate) : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderGifListBinding

    private var mData:Data? = null

    init {
        binding = ViewHolderGifListBinding.bind(itemView)

        itemView.setOnClickListener {
            delegate.onTapGifImage(mData?.images?.original?.url ?: "")
        }
    }

    fun bindData(data: Data) {

        mData = data

        val image = data.images?.original?.url ?: ""

        Glide.with(itemView.context)
            .load(image)
            .into(binding.ivGif)


    }
}