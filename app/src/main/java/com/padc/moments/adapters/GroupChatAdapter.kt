package com.padc.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.R
import com.padc.moments.data.vos.GroupVO
import com.padc.moments.delegates.GroupItemActionDelegate
import com.padc.moments.views.viewholders.GroupChatViewHolder

class GroupChatAdapter(private val delegate:GroupItemActionDelegate) : RecyclerView.Adapter<GroupChatViewHolder>() {

    private var mGroupList:ArrayList<GroupVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_list,parent,false)
        return GroupChatViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: GroupChatViewHolder, position: Int) {
        holder.bindData(mGroupList[position])
    }

    override fun getItemCount(): Int {
        return mGroupList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(group: GroupVO) {
        mGroupList.add(group)
        notifyDataSetChanged()
    }
}
