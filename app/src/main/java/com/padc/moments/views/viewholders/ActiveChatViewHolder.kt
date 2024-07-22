package com.padc.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.databinding.ViewHolderActiveChatListBinding

class ActiveChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderActiveChatListBinding
    init {
        binding = ViewHolderActiveChatListBinding.bind(itemView)
    }


}