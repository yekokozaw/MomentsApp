package com.padc.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.R
import com.padc.moments.data.vos.PrivateMessageVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.delegates.ChatItemActionDelegate
import com.padc.moments.views.viewholders.ChatViewHolder

class ChatAdapter(private val delegate:ChatItemActionDelegate) : RecyclerView.Adapter<ChatViewHolder>() {

    private var mUserList:List<UserVO> = arrayListOf()
    private var mMessageList:List<PrivateMessageVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_list,parent,false)
        return ChatViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        holder.bindData(mUserList[position], mMessageList[position])
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(userList: ArrayList<UserVO>, messageList: ArrayList<PrivateMessageVO>) {
        val userMessagePair = ArrayList<Pair<UserVO, PrivateMessageVO>>()
        for (i in userList.indices) {
            val user = userList[i]
            val message = messageList[i]
            val pair = user to message
            userMessagePair.add(pair)
        }
        userMessagePair.sortByDescending {
            it.second.timeStamp
        }

        val sortedUserList = mutableListOf<UserVO>()
        val sortedMessageList = mutableListOf<PrivateMessageVO>()

        for (pair in userMessagePair) {
            sortedUserList.add(pair.first)
            sortedMessageList.add(pair.second)
        }
        mUserList = sortedUserList
        mMessageList = sortedMessageList
        notifyDataSetChanged()
    }

}
