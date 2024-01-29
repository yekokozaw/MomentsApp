package com.padc.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.R
import com.padc.moments.data.vos.GroupVO
import com.padc.moments.delegates.GroupItemActionDelegate
import com.padc.moments.views.viewholders.GroupViewHolder
import java.util.ArrayList

class GroupAdapter(private val delegate: GroupItemActionDelegate) :
    RecyclerView.Adapter<GroupViewHolder>() {

    private var mGroupList: ArrayList<GroupVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_group_list, parent, false)
        return GroupViewHolder(view, delegate)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bindData(mGroupList[position])
    }

    override fun getItemCount(): Int {
        return mGroupList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(groupList: ArrayList<GroupVO>) {
        mGroupList = groupList
        notifyDataSetChanged()
    }
}