package com.padc.moments.mvp.views

interface RegisterView : BaseView {
    fun navigateToPreviousScreen()
    fun navigateToLoginScreen()
    fun showGallery()
    fun openCamera()
}