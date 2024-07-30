package com.company.teacherforboss.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.SnackbarCustomBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.Snackbar

class CustomSnackBar(view: View, private val msg: String, private val duration: Int) {

    companion object {
        fun make(view: View, msg: String, duration: Int) = CustomSnackBar(view, msg, duration)
    }

    private val context = view.context
    private val snackBar = Snackbar.make(view, "", duration)
    @SuppressLint("RestrictedApi")
    private val snackbarView = snackBar.view as Snackbar.SnackbarLayout
    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: SnackbarCustomBinding = DataBindingUtil.inflate(inflater, R.layout.snackbar_custom, null, false)
    init {
        initView()
        initData()
    }

    @SuppressLint("RestrictedApi")
    private fun initView() {
        with(snackbarView) {
            removeAllViews()
            setPadding(15, 0, 15, 30)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.snackbarText.text = msg
    }

    fun show() {
        snackBar.animationMode = ANIMATION_MODE_FADE
        snackBar.show()
    }
}

