package com.padc.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.moments.data.vos.UserVO
import com.padc.moments.databinding.ViewHolderActiveChatListBinding
import com.padc.moments.network.storage.PresenceManager

class ActiveChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderActiveChatListBinding
    private lateinit var mPresenceManager: PresenceManager
    init {
        binding = ViewHolderActiveChatListBinding.bind(itemView)
    }

    fun bindData(user: UserVO) {
        mPresenceManager = PresenceManager(user.userId)
        mPresenceManager.checkState(
            onSuccess = {
                if (it){
                    binding.chipActive.visibility = View.VISIBLE

                }else{
                    binding.chipActive.visibility = View.GONE
                }

            },
            onFailure = { }
        )
        Glide.with(itemView.context)
            .load(user.imageUrl)
            .into(binding.ivProfileImageActiveChat)

    }

}