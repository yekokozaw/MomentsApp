package com.padc.moments.views.viewholders

import android.text.format.DateUtils
import android.view.View
import com.bumptech.glide.Glide
import com.padc.moments.data.vos.PrivateMessageVO
import com.padc.moments.databinding.ViewHolderMessageReceiveBinding
import com.padc.moments.delegates.ChatDetailsImageDelegate

class MessageReceiveViewHolder(private val delegate: ChatDetailsImageDelegate,itemView: View)
    : IBaseMessageViewHolder(itemView) {

    private var binding:ViewHolderMessageReceiveBinding
    private var imageUrl : String? = null
    init {
        binding = ViewHolderMessageReceiveBinding.bind(itemView)
        binding.ivReceiveImage.setOnClickListener {
            imageUrl?.let { it1 -> delegate.onTapImage(it1) }
        }
    }

    override fun bindData(message: PrivateMessageVO) {
        imageUrl = message.file
        Glide.with(itemView.context)
            .load(message.userProfileImage)
            .into(binding.ivProfileChatHead)

        if (message.message.isEmpty()) {
            binding.rlChatMessage.visibility = View.GONE
            binding.mcvReceiveImageChatDetail.visibility = View.VISIBLE

            Glide.with(itemView.context)
                .load(message.file)
                .into(binding.ivReceiveImage)

        } else if (message.message.isNotEmpty() && message.file.isEmpty()) {
            binding.rlChatMessage.visibility = View.VISIBLE
            binding.mcvReceiveImageChatDetail.visibility = View.GONE

            binding.tvReceivedMessage.text = message.message
            binding.tvTimeReceiveMessage.text = getTimeAgo(message.timeStamp)
        } else {
            binding.rlChatMessage.visibility = View.VISIBLE
            binding.mcvReceiveImageChatDetail.visibility = View.VISIBLE

            binding.tvReceivedMessage.text = message.message
            binding.tvTimeReceiveMessage.text = getTimeAgo(message.timeStamp)

            Glide.with(itemView.context)
                .load(message.file)
                .into(binding.ivReceiveImage)
        }
    }

//    private fun getCurrentHourAndMinutes(currentTimeMillis:Long) :String {
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = currentTimeMillis
//
//        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
//        val minutes = calendar.get(Calendar.MINUTE)
//
//        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
//        return timeFormat.format(calendar.time)
//    }

    fun getTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val timeAgo = DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.MINUTE_IN_MILLIS)
        return timeAgo.toString()
    }
}