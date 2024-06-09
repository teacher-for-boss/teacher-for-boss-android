package com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.teacherforboss.databinding.DialogDeleteCommentBinding

class DeleteCommentDialog(context: Context): Dialog(context) {
    private lateinit var binding: DialogDeleteCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeleteCommentBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)

        binding.keepBtn.setOnClickListener {
            dismiss()
        }

        binding.deleteBtn.setOnClickListener {  }
    }
}