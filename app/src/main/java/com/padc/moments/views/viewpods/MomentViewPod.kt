package com.padc.moments.views.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.moments.adapters.MomentAdapter
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.databinding.ViewPodPostBinding
import com.padc.moments.delegates.MomentItemActionDelegate

class MomentViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var binding:ViewPodPostBinding
    private lateinit var mAdapter:MomentAdapter

    private lateinit var mDelegate:MomentItemActionDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewPodPostBinding.bind(this)

    }

    fun setDelegate(delegate:MomentItemActionDelegate) {
        this.mDelegate = delegate
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapter = MomentAdapter(mDelegate)
        binding.rvMoment.adapter = mAdapter
        binding.rvMoment.layoutManager = LinearLayoutManager(context)
    }

    fun setNewData(momentList: List<MomentVO>, tabName: String) {
        mAdapter.setNewData(momentList,tabName)
    }
}