package com.padc.moments.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.padc.moments.R

fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun showSnackBar(view : View,message : String){
    Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show()
}

fun showSuccessSnackBar(view : View,message : String){
    val snackbar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT)
    snackbar.setBackgroundTint(ContextCompat.getColor(view.context,R.color.colorLightBlue))
    snackbar.show()
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