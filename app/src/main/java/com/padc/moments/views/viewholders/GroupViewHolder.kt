package com.padc.moments.views.viewholders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.moments.R
import com.padc.moments.data.vos.GroupVO
import com.padc.moments.databinding.ViewHolderGroupListBinding
import com.padc.moments.delegates.GroupItemActionDelegate

class GroupViewHolder(itemView: View, private val delegate: GroupItemActionDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderGroupListBinding

    init {
        binding = ViewHolderGroupListBinding.bind(itemView)
    }

    fun bindData(group: GroupVO) {
//        if (adapterPosition == 0) {
//            binding.llGroupItem.setBackgroundResource(R.drawable.background_group_list_accent)
//            binding.ivGroupPhoto.setImageResource(R.drawable.dummy_group_example_photo)
//            binding.tvGroupName.text = "Add New"
//            binding.tvGroupName.setTextColor(Color.WHITE)
//
//            itemView.setOnClickListener {
//                delegate.onTapGroupItem()
//            }
//        } else {
//            binding.tvGroupName.text = group.name
//        }
        binding.tvGroupName.text = group.name

        Log.i("GroupCover",group.imageUrl)

        Glide.with(itemView.context)
            .load(group.imageUrl)
            .placeholder(R.drawable.dummy_group_photo)
            .into(binding.ivGroupPhoto)

        itemView.setOnClickListener {
            delegate.onTapGroupItem(group.id)
        }
    }

}