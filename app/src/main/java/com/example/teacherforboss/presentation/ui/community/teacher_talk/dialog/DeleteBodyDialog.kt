package com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.databinding.DialogDeleteBodyBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


class DeleteBodyDialog(context: Context,
                       private val viewModel: TeacherTalkBodyViewModel,
                       private val lifecycleOwner: LifecycleOwner, val id: Long
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
                viewModel.deletePost()
            }

            viewModel.deleteLiveData.observe(lifecycleOwner, Observer{
                Log.d("delete", it.questionId.toString())
                dismiss()
                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("gotoTeacherTalk", "gotoTeacherTalk")
                }
                context.startActivity(intent)
            })
        }
    }
}