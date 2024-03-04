package com.example.teacherforboss.util.context

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import java.time.Duration

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.stringOf(@StringRes resId: Int) = getString(resId)

fun Context.showToast(msg:String,duration: Int= Toast.LENGTH_LONG){
    Toast.makeText(this,msg,duration).show()
}
