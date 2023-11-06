package com.padc.moments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.R
import com.padc.moments.data.ImageItem
import com.padc.moments.views.viewholders.GalleryImagesViewHolder

class GalleryImagesAdapter(private val context: Context, private val images: List<ImageItem>) :
    RecyclerView.Adapter<GalleryImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryImagesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_gallery_images_list, parent, false)
        return GalleryImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryImagesViewHolder, position: Int) {
        val imageItem = images[position]
        holder.bind(imageItem)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}

