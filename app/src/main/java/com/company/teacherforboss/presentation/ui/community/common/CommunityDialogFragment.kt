package com.company.teacherforboss.presentation.ui.community.common

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.DialogCommunityBinding
import com.company.teacherforboss.util.base.BindingDialogFragment

class CommunityDialogFragment(
    private val message: String,
    private val leftBtnText: String,
    private val rightBtnText: String,
    private val clickLeftBtn: () -> Unit,
    private val clickRightBtn: () -> Unit,
    private val closeDialog: () -> Unit = {}
): BindingDialogFragment<DialogCommunityBinding>(R.layout.dialog_community) {
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
            tvDialogMessage.text = message
            btnDialogLeft.text = leftBtnText
            btnDialogRight.text = rightBtnText
        }
    }

    private fun addListeners() {
        binding.btnDialogLeft.setOnClickListener {
            clickLeftBtn()
            dismiss()
        }

        binding.btnDialogRight.setOnClickListener {
            clickRightBtn()
            dismiss()
        }

        binding.layoutDialogDeleteBackground.setOnClickListener {
            dismiss()
        }
    }
}