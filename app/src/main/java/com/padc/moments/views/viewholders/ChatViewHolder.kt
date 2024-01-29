package com.padc.moments.views.viewholders

import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.moments.data.vos.PrivateMessageVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.databinding.ViewHolderChatListBinding
import com.padc.moments.delegates.ChatItemActionDelegate

class ChatViewHolder(itemView: View,private val delegate: ChatItemActionDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderChatListBinding
    private var mUserId:String? = null

    init {
        binding = ViewHolderChatListBinding.bind(itemView)

        setUpListeners()
    }

    private fun setUpListeners() {
        itemView.setOnClickListener {
            mUserId?.let { userId ->
                delegate.onTapChatItem(userId)
            }
        }
    }

    fun bindData(user: UserVO, message: PrivateMessageVO) {

        mUserId = user.userId

        binding.tvNameChat.text = user.userName

        Glide.with(itemView.context)
            .load(user.imageUrl)
            .into(binding.ivProfileImageChat)

        if(message.message.isEmpty()) {
            val lastMessage = "${message.userName} sent a photo"
            binding.tvLastMessage.text = lastMessage
        } else {
            binding.tvLastMessage.text = message.message
        }

        binding.tvLastOnlineChat.text = getTimeAgo(message.timeStamp)

    }

    fun getTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val timeAgo = DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.MINUTE_IN_MILLIS)
        return timeAgo.toString()
    }

}