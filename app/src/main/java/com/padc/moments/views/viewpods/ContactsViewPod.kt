package com.padc.moments.views.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.moments.adapters.AlphabetAdapter
import com.padc.moments.adapters.ContactsAlphabetGroupAdapter
import com.padc.moments.data.vos.UserVO
import com.padc.moments.databinding.ViewPodContactsBinding
import com.padc.moments.delegates.AlphabetActionItemDelegate
import com.padc.moments.delegates.ChatItemActionDelegate
import com.padc.moments.utils.GeneralListData

class ContactsViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var binding:ViewPodContactsBinding
    private lateinit var mDelegateChat: ChatItemActionDelegate
    private lateinit var mDelegateAlphabet: AlphabetActionItemDelegate

    // Adapters
    private lateinit var mAlphabetAdapter: AlphabetAdapter
    private lateinit var mContactsAlphabetGroupAdapter: ContactsAlphabetGroupAdapter

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewPodContactsBinding.bind(this)
    }

    fun setDelegate(delegateChat: ChatItemActionDelegate,delegateAlphabet: AlphabetActionItemDelegate) {
        this.mDelegateChat = delegateChat
        this.mDelegateAlphabet = delegateAlphabet
        setUpAlphabetAdapter()
        setUpContactsAlphabetGroupAdapter()
    }

    private fun setUpAlphabetAdapter() {
        mAlphabetAdapter = AlphabetAdapter(GeneralListData.getAlphabetList(),mDelegateAlphabet)
        binding.rvAlphabet.adapter = mAlphabetAdapter
        binding.rvAlphabet.layoutManager = LinearLayoutManager(context)
    }

    private fun setUpContactsAlphabetGroupAdapter() {
        mContactsAlphabetGroupAdapter = ContactsAlphabetGroupAdapter(mDelegateChat)
        binding.rvContactsAlphabetGroup.adapter = mContactsAlphabetGroupAdapter
        binding.rvContactsAlphabetGroup.layoutManager = LinearLayoutManager(context)
    }

    fun setNewData(alphabetList: List<Char>, contactList: List<UserVO>,isGroup:Boolean) {
        mContactsAlphabetGroupAdapter.setNewData(alphabetList,contactList,isGroup)
    }

    fun getItemCount():Int {
        return mContactsAlphabetGroupAdapter.getUserCount()
    }
}