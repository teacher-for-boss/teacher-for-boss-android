package com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.databinding.DialogWriteExitBinding

interface WriteExitDialogListener{
    fun onExitBtnClicked()
}

class WriteExitDialog(context: Context,val type:String,val purpose:String,val listener: WriteExitDialogListener): Dialog(context) {
    private lateinit var binding: DialogWriteExitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogWriteExitBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)

        binding.keepBtn.setOnClickListener {
            dismiss()
        }

        binding.exitBtn.setOnClickListener {
            if(purpose=="write"){
                val intent=Intent(context,MainActivity::class.java).apply {
                    putExtra("FRAGMENT_DESTINATION",type.toString())
                }
                context.startActivity(intent)
            }
            else{
                listener.onExitBtnClicked()

            }
            dismiss()
        }
    }

}