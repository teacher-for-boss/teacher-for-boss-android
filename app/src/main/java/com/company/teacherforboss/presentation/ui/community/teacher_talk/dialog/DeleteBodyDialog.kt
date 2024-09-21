package com.company.teacherforboss.presentation.ui.community.teacher_talk.dialog

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
import com.company.teacherforboss.MainActivity
import androidx.lifecycle.ViewModel
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.DialogDeleteBodyBinding
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyViewModel
import kotlinx.coroutines.launch
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyViewModel
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_TALK
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SNACK_BAR_MSG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK


class DeleteBodyDialog<T: ViewModel>(context: Context,
                                     private val viewModel: T,
                                     private val lifecycleOwner: LifecycleOwner,
                                     val id: Long,
                                     val role: String
): Dialog(context) {
    private lateinit var binding: DialogDeleteBodyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeleteBodyBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)

        initLayout()
        dismissDialog()
        deleteBody()
    }

    private fun initLayout() {
        with(binding) {
            if(role == BOSS_TALK) { text.text = context.getString(R.string.dialog_delete_boss_body) }
            else { text.text = context.getString(R.string.dialog_delete_teacher_body) }
        }
    }

    private fun dismissDialog() {
        binding.keepBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun deleteBody() {
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
                        putExtra(FRAGMENT_DESTINATION, TEACHER_TALK)
                        putExtra(SNACK_BAR_MSG, context.getString(R.string.community_question_deleted))
                    }
                    context.startActivity(intent)
                })
            } else if (viewModel is BossTalkBodyViewModel) {
                viewModel.deleteLiveData.observe(lifecycleOwner, Observer {
                    val intent = Intent(context, MainActivity::class.java).apply {
                        putExtra(FRAGMENT_DESTINATION, BOSS_TALK)
                        putExtra(SNACK_BAR_MSG, context.getString(R.string.community_post_deleted))
                    }
                    context.startActivity(intent)
                    dismiss()

                })
            }
        }
    }
}