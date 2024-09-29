package com.company.teacherforboss.util.component

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.DialogPopupBinding
import com.company.teacherforboss.util.base.BindingDialogFragment

class DialogPopupFragment(
    private val title: String,
    private val content: String,
    private val leftBtnText: String,
    private val rightBtnText: String,
    private val clickLeftBtn: () -> Unit,
    private val clickRightBtn: () -> Unit,
    private val closeDialog: () -> Unit = {},
    private val backgroundClickable: Boolean = true,
    private val clickBackground: () -> Unit = {}
) : BindingDialogFragment<DialogPopupBinding>(R.layout.dialog_popup) {
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
            tvDialogPopupTitle.text = title
            if (content.isNullOrEmpty()) {
                tvDialogPopupContent.visibility = View.GONE
            }
            else { tvDialogPopupContent.text = content }
            if(leftBtnText.isNullOrEmpty()) {
                btnDialogPopupLeft.visibility = View.GONE
            }
            else { btnDialogPopupLeft.text = leftBtnText }
            btnDialogPopupRight.text = rightBtnText
        }
    }

    private fun addListeners() {
        binding.btnDialogPopupLeft.setOnClickListener {
            clickLeftBtn()
            dismiss()
        }

        binding.btnDialogPopupRight.setOnClickListener {
            clickRightBtn()
            dismiss()
        }

        if(backgroundClickable) {
            binding.layoutDialogPopupBackground.setOnClickListener {
                clickBackground()
                dismiss()
            }
        }
    }
}