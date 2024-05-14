package com.example.teacherforboss.util

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.SnackbarCustomBinding
import com.google.android.material.snackbar.Snackbar

class CustomSnackBar(view: View, private val msg:String,private val duration:Int,private val location:View) {

    companion object{
        fun make(view:View,msg:String,duration:Int,location: View)=CustomSnackBar(view,msg, duration,location)
    }

    private val context=view.context
    private val snackBar=Snackbar.make(view,"",duration)
    private val snackbarLayout=snackBar.view as Snackbar.SnackbarLayout

    private val inflater=LayoutInflater.from(context)
    private val snackbarBinding:SnackbarCustomBinding=DataBindingUtil.inflate(inflater,R.layout.snackbar_custom,null,false)

    init {
        initView()
        initData()

    }
    private fun initView(){
        with(snackbarLayout){
            removeAllViews()
            setPadding(0,0,0,0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root,0)
        }
    }
    private fun initData(){
        snackbarBinding.snackbarText.text=msg
    }
    fun show(){
        snackBar.anchorView=location
        snackBar.show()
    }

}