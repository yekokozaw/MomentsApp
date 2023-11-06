package com.padc.moments.mvp.interfaces

import androidx.lifecycle.LifecycleOwner
import com.padc.moments.mvp.views.BaseView

interface BasePresenter<V : BaseView> {

    fun initPresenter(view: V)
    fun onUIReady(lifecycleOwner: LifecycleOwner)
}