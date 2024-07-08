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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.databinding.DialogDeleteBodyBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyViewModel
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyViewModel
import kotlinx.coroutines.launch

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
                    /*viewModel.deletePost(id)*/
                } else if (viewModel is BossTalkBodyViewModel) {
                    viewModel.deletePost(id) //TODO: 보스톡 게시글 삭제 구현후 테스트
                }
            }

            if(viewModel is TeacherTalkBodyViewModel) {
                /*viewModel.deleteLiveData.observe(lifecycleOwner, Observer {
                    Log.d("deleteTeacherTalk", it.questionId.toString())
                    dismiss()
                    val intent = Intent(context, MainActivity::class.java).apply {
                        putExtra("gotoTeacherTalk", "gotoTeacherTalk")
                    }
                    context.startActivity(intent)
                })*/
            } else if (viewModel is BossTalkBodyViewModel) {
            viewModel.deleteLiveData.observe(lifecycleOwner, Observer {
                dismiss()
                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("gotoBossTalk", "gotoBossTalk")
                }
                context.startActivity(intent)
            })
            }
        }
    }
}