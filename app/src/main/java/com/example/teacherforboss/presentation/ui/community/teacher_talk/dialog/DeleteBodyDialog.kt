package com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.MainActivity
import androidx.lifecycle.ViewModel
import com.example.teacherforboss.databinding.DialogDeleteBodyBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyViewModel
import kotlinx.coroutines.launch
import com.example.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyViewModel


class DeleteBodyDialog<T: ViewModel>(context: Context,
                                     private val viewModel: T,
                                     private val lifecycleOwner: LifecycleOwner,
                                     val id: Long
): Dialog(context) {
    private lateinit var binding: DialogDeleteBodyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeleteBodyBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)


        //나가기 취소
        binding.keepBtn.setOnClickListener {
            dismiss()
        }
        //나가기
        binding.deleteBtn.setOnClickListener {

            lifecycleOwner.lifecycleScope.launch {

                if (viewModel is TeacherTalkBodyViewModel) {
                    viewModel.deletePost()
                } else if (viewModel is BossTalkBodyViewModel) {
                    viewModel.deletePost()
                }

            }

            if(viewModel is TeacherTalkBodyViewModel) {
                viewModel.deleteLiveData.observe(lifecycleOwner, Observer {
                    dismiss()
                    val intent = Intent(context, MainActivity::class.java).apply {
                        putExtra("FRAGMENT_DESTINATION", "TEACHER_TALK")
                    }
                    context.startActivity(intent)
                })
            } else if (viewModel is BossTalkBodyViewModel) {
            viewModel.deleteLiveData.observe(lifecycleOwner, Observer {
                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("FRAGMENT_DESTINATION", "BOSS_TALK")
                }
                context.startActivity(intent)
                dismiss()

            })
            }
        }
    }
}