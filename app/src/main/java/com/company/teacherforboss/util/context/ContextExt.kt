package com.company.teacherforboss.util.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import com.company.teacherforboss.presentation.common.WebViewActivity
import com.company.teacherforboss.presentation.common.WebViewActivity.Companion.WEB_VIEW_LINK

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.navigateToWebView(link: String) = Intent(this, WebViewActivity::class.java).apply {
    putExtra(WEB_VIEW_LINK, link)
}

fun Context.stringOf(@StringRes resId: Int) = getString(resId)

fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, msg, duration).show()
}
