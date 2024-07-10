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

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        // TODO LocalDataSource에서 가져오는 걸로 수정
        val role = "TEACHER"
        if (role == ROLE_TEACHER) {
            with(binding) {
                tvMyPageLevel.visibility = View.VISIBLE
                tvMyPageMenuBarThird.text = getString(R.string.my_page_teacher_menu_bar_tp)
                ivMyPageMenuBarThird.setImageResource(R.drawable.ic_teacher_point_30)
                layoutMyPageLevelInfo.visibility = View.VISIBLE
                tvMyPageMenuReward.text = getString(R.string.my_page_reward_title)
                includeMyPageMenuAccountChange.title =
                    getString(R.string.my_page_menu_account_change)
                includeMyPageMenuExchange.title = getString(R.string.my_page_menu_exchange)
                includeMyPageMenuExchangeDetails.apply {
                    root.visibility = View.VISIBLE
                    title = getString(R.string.my_page_menu_exchange_details)
                }
                includeMyPageMenuTeacherTalkQuestionPost.title =
                    getString(R.string.my_page_menu_teacher_talk_answered_post)
            }
        } else {
            with(binding) {
                includeMyPageMenuAccountChange.title =
                    getString(R.string.my_page_menu_payment_question_ticket)
                includeMyPageMenuExchange.title = getString(R.string.my_page_menu_payment_history)
                includeMyPageMenuExchangeDetails.root.visibility = View.GONE
                includeMyPageMenuTeacherTalkQuestionPost.title =
                    getString(R.string.my_page_menu_teacher_talk_question_post)
            }
        }
    }

    private fun addListeners() {
        with(binding) {
            includeMyPageMenuInquire.root.setOnClickListener {
                requireActivity().startActivity(
                    requireActivity().navigateToWebView(INQUIRE_WEB_LINK),
                )
            }
            includeMyPageMenuTerms.root.setOnClickListener {
                requireActivity().startActivity(
                    requireActivity().navigateToWebView(TERMS_WEB_LINK),
                )
            }
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
        private const val ROLE_TEACHER = "TEACHER"
        private const val ROLE_BOSS = "BOSS"
    }
}
