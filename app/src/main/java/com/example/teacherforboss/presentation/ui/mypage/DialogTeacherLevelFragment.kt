package com.example.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.DialogTeacherLevelBinding
import com.example.teacherforboss.util.base.BindingDialogFragment

class DialogTeacherLevelFragment(
    private val closeDialog: () -> Unit = {},
) : BindingDialogFragment<DialogTeacherLevelBinding>(R.layout.dialog_teacher_level) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
    }

    override fun dismiss() {
        super.dismiss()
        closeDialog()
    }

    private fun addListeners() {
        binding.layoutDialogTeacherLevelBackground.setOnClickListener { dismiss() }
    }
}
