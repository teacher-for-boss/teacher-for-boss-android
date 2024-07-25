package com.company.teacherforboss.presentation.ui.community.teacher_talk.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.databinding.DialogDeleteCommentBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyViewModel
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyViewModel
import kotlinx.coroutines.launch

class DeleteCommentDialog<T: ViewModel>(
    context: Context,
    private val viewModel: T,
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
            lifecycleOwner.lifecycleScope.launch {
                if(viewModel is TeacherTalkBodyViewModel) {
                    viewModel.deleteAnswer()
                }
                else if (viewModel is BossTalkBodyViewModel) {
                    viewModel.deleteComment()
                }
            }

            if(viewModel is TeacherTalkBodyViewModel) {
                viewModel.deleteAnsLiveData.observe(lifecycleOwner, Observer {
                    dismiss()
                })
            }
            else if(viewModel is BossTalkBodyViewModel) {
                viewModel.deleteCommentLiveData.observe(lifecycleOwner, Observer {
                    dismiss()
                })
            }
        }
    }
}