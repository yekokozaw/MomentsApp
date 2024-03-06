package com.padc.moments.mvp.views

interface RegisterView : BaseView {
    fun navigateToPreviousScreen()
    fun navigateBack()
    fun showGallery()
    fun openCamera()
}