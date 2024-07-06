package com.example.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentMyPageBinding
import com.example.teacherforboss.util.base.BindingFragment

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initLayout() {
        if (true) { // 보스인 경우 -> 티처를 디폴트로 하고 보스를 뷰 수정하는 것이 좋을듯
            with(binding) {
                tvMyPageLevel.visibility = View.GONE
                layoutMyPageLevelInfo.visibility = View.GONE
                // menu bar setImage
                // menu bar String 수정
            }
        }
    }
}
