package com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.teacherforboss.databinding.DialogDeleteCommentBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyViewModel

class DeleteCommentDialog(
    context: Context,
    private val viewModel: TeacherTalkBodyViewModel,
    private val lifecycleOwner: LifecycleOwner
    ): Dialog(context) {
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

        binding.deleteBtn.setOnClickListener {
            viewModel.deleteAnswer()
        }
        viewModel.deleteAnsLiveData.observe(lifecycleOwner, Observer {
            dismiss()
        })
    }
}