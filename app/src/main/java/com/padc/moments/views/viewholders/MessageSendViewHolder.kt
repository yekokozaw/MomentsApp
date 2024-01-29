package com.padc.moments.views.viewholders

import android.text.format.DateUtils
import android.view.View
import com.bumptech.glide.Glide
import com.padc.moments.data.vos.PrivateMessageVO
import com.padc.moments.databinding.ViewHolderMessageSendBinding
import com.padc.moments.delegates.ChatDetailsImageDelegate

class MessageSendViewHolder(private val delegate: ChatDetailsImageDelegate,itemView: View)
    : IBaseMessageViewHolder(itemView) {

    private var binding: ViewHolderMessageSendBinding
    private var imageUrl : String? = null
    init {
        binding = ViewHolderMessageSendBinding.bind(itemView)
        binding.ivSendImageChatDetail.setOnClickListener {
            imageUrl?.let { it1 -> delegate.onTapImage(it1) }
        }
    }

    override fun bindData(message: PrivateMessageVO) {
        imageUrl = message.file
        if (message.message.isEmpty() && message.file.isNotEmpty()) {
            binding.flSendMessage.visibility = View.GONE
            binding.mcvSendImageChatDetail.visibility = View.VISIBLE

            Glide.with(itemView.context)
                .load(message.file)
                .into(binding.ivSendImageChatDetail)

        } else if (message.message.isNotEmpty() && message.file.isEmpty()) {
            binding.flSendMessage.visibility = View.VISIBLE
            binding.mcvSendImageChatDetail.visibility = View.GONE

            binding.tvSentMessage.text = message.message
            binding.tvTimeSendMessage.text = getTimeAgo(message.timeStamp)
        } else {
            binding.flSendMessage.visibility = View.VISIBLE
            binding.mcvSendImageChatDetail.visibility = View.VISIBLE

            binding.tvSentMessage.text = message.message
            binding.tvTimeSendMessage.text = getTimeAgo(message.timeStamp)

            Glide.with(itemView.context)
                .load(message.file)
                .into(binding.ivSendImageChatDetail)
        }
    }

    fun getTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val timeAgo = DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.MINUTE_IN_MILLIS)
        return timeAgo.toString()
    }

//    private fun getCurrentHourAndMinutes(currentTimeMillis: Long): String {
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = currentTimeMillis
//
//        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
//        val minutes = calendar.get(Calendar.MINUTE)
//
//        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
//        return timeFormat.format(calendar.time)
//    }
}