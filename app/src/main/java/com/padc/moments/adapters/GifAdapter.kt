package com.padc.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.R
import com.padc.moments.data.vos.giphy.Data
import com.padc.moments.delegates.GifItemActionDelegate
import com.padc.moments.views.viewholders.GifViewHolder

class GifAdapter(private val delegate:GifItemActionDelegate) : RecyclerView.Adapter<GifViewHolder>() {

    private var mGifList:List<Data> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_gif_list,parent,false)
        return GifViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bindData(mGifList[position])
    }

    override fun getItemCount(): Int {
        return mGifList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<Data>) {
        mGifList = data
        notifyDataSetChanged()
    }
}