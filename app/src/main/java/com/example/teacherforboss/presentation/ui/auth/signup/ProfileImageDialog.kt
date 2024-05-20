package com.example.teacherforboss.presentation.ui.auth.signup

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.LifecycleOwner
import com.example.teacherforboss.databinding.DialogProfileImageBinding

class ProfileImageDialog (context: Context,
                          private val viewModel: SignupViewModel,
                          private val lifecycleOwner: LifecycleOwner
): Dialog(context), LifecycleOwner by lifecycleOwner {
    private lateinit var binding: DialogProfileImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DialogProfileImageBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)


        setView()

        setOnShowListener {
        }
    }

    private fun setView() {
        // Dialog 크기 및 위치 설정
        val layoutParams = window?.attributes
        window?.attributes = layoutParams
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.requestFeature(Window.FEATURE_NO_TITLE)


        setCanceledOnTouchOutside(true) // 다이얼로그 바깥쪽 클릭시 종료 ->false 불가능

        setCancelable(true) //취소 가능 여부
    }


}