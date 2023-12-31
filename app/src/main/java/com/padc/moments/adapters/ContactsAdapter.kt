package com.padc.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.moments.R
import com.padc.moments.data.vos.UserVO
import com.padc.moments.delegates.ChatItemActionDelegate
import com.padc.moments.views.viewholders.ContactsViewHolder

class ContactsAdapter(private val delegate:ChatItemActionDelegate) : RecyclerView.Adapter<ContactsViewHolder>() {

     private var mNameList:ArrayList<UserVO> = arrayListOf()
    private var mIsGroup = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_contacts_list,parent,false)
        return ContactsViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindData(mNameList[position],mIsGroup)
    }

    override fun getItemCount(): Int {
        return mNameList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(nameList: ArrayList<UserVO>, isGroup: Boolean) {
        mNameList = nameList
        mIsGroup = isGroup
        notifyDataSetChanged()
    }
}