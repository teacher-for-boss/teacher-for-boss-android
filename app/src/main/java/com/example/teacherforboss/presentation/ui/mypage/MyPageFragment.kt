package com.example.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentMyPageBinding
import com.example.teacherforboss.util.base.BindingFragment
import com.example.teacherforboss.util.component.DialogPopupFragment
import com.example.teacherforboss.util.context.navigateToWebView

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
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

    private fun addListeners() {
        with(binding) {
            includeMyPageMenuInquire.root.setOnClickListener { requireActivity().startActivity(requireActivity().navigateToWebView(INQUIRE_WEB_LINK)) }
            includeMyPageMenuTerms.root.setOnClickListener { requireActivity().startActivity(requireActivity().navigateToWebView(TERMS_WEB_LINK)) }
            tvLogOutBtn.setOnClickListener { showLogoutDialogFragment() }
        }
    }

    private fun showLogoutDialogFragment() {
        // TODO clickRightBtn에 로그아웃 뷰모델 로직 추가
        DialogPopupFragment(
            title = getString(R.string.dialog_logout_title),
            content = getString(R.string.dialog_logout_content),
            leftBtnText = getString(R.string.dialog_exit),
            rightBtnText = getString(R.string.dialog_logout_btn),
            clickLeftBtn = {},
            clickRightBtn = {},
        ).show(parentFragmentManager, LOGOUT_DIALOG)
    }

    companion object {
        private const val INQUIRE_WEB_LINK =
            "https://docs.google.com/forms/d/e/1FAIpQLScvoVxh-1jlqyKhVKiFS4pZDhk-GtYbZOHKh4KJHveutN2TYw/viewform"
        private const val TERMS_WEB_LINK =
            "https://beautiful-pharaoh-385.notion.site/3f2236a9632b4edca4b7a0175308f43b?pvs=4"
        private const val LOGOUT_DIALOG = "logoutModal"
    }
}
