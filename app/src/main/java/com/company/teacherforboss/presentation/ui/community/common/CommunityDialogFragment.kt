package com.company.teacherforboss.presentation.ui.community.common

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.DialogDeleteCommentBinding
import com.company.teacherforboss.util.base.BindingDialogFragment

class CommunityDialogFragment(
    private val title: String,
    private val leftBtnText: String,
    private val rightBtnText: String,
    private val clickLeftBtn: () -> Unit,
    private val clickRightBtn: () -> Unit,
    private val closeDialog: () -> Unit = {}
): BindingDialogFragment<DialogDeleteCommentBinding>(R.layout.dialog_delete_comment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        closeDialog()
    }

    private fun initLayout() {
        with(binding) {
            text.text = title
            tvLeftBtn.text = leftBtnText
            tvRightBtn.text = rightBtnText
        }
    }

    private fun addListeners() {
        binding.layoutDialogPopupBackground.setOnClickListener {
            dismiss()
        }
        binding.keepBtn.setOnClickListener {
            clickLeftBtn()
            dismiss()
        }
        binding.deleteBtn.setOnClickListener {
            clickRightBtn()
            dismiss()
        }
    }
}