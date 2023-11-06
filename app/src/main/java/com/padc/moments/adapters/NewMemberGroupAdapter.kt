package com.padc.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.R
import com.padc.moments.data.vos.UserVO
import com.padc.moments.views.viewholders.NewMemberViewHolder

class NewMemberGroupAdapter : RecyclerView.Adapter<NewMemberViewHolder>() {

    private var mMemberList: ArrayList<UserVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_new_member_list, parent, false)
        return NewMemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewMemberViewHolder, position: Int) {
        if (mMemberList.isNotEmpty()) {
            holder.bindData(mMemberList[position])
        }
    }

    override fun getItemCount(): Int {
        return mMemberList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(memberList: ArrayList<UserVO>) {
        mMemberList = memberList
        notifyDataSetChanged()
    }
}