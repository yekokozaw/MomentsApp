package com.padc.moments.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.padc.moments.R

fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

//fun View.isShownOnScreen() : Boolean{
//    return visibility == View.VISIBLE
//}

//fun AppCompatActivity.showToast(message: String){
//    Toast.makeText(this,message, Toast.LENGTH_LONG).show()
//}
//
//fun Context.showErrorToast(message: String) {
//    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
//        // Customize the toast layout
//        view?.setBackgroundResource(R.drawable.error_background)
//    }.show()
//}