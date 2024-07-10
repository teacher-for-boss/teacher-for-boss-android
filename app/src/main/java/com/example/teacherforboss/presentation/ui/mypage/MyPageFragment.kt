package com.example.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentMyPageBinding
import com.example.teacherforboss.presentation.type.TeacherLevelType
import com.example.teacherforboss.util.base.BindingFragment
import com.example.teacherforboss.util.component.DialogPopupFragment
import com.example.teacherforboss.util.context.navigateToWebView

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: MyPageViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPageViewModel = viewModel

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        // TODO role은 viewModel 통해서 LocalDataSource에서 가져오는 걸로 수정
        val role = "TEACHER"
        if (role == ROLE_TEACHER) {
            setTeacherProfileLayout()
            setTeacherMenuBarLayout()
            setTeacherMenuLayout()
        } else {
            setBossProfileLayout()
            setBossMenuLayout()
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
            layoutMyPageLevelInfo.setOnClickListener { showTeacherLevelDialogFragment() }
        }
    }

    private fun setTeacherProfileLayout() {
        with(binding) {
            // TODO 사용자이름은 서버통신인지 local 저장인지 모르겠지만 암튼 둘중 하나로~
            tvMyPageProfileName.text = getString(R.string.my_page_teacher_name, "나는티처다")
            tvMyPageLevel.apply {
                // TODO Mapper 사용해서 서버로 받아온 레벨 Type값과 매핑
                text = getString(TeacherLevelType.LEVEL5.levelName)
                visibility = View.VISIBLE
            }
            layoutMyPageLevelInfo.visibility = View.VISIBLE
            tvMyPageLevelInfo.apply {
                // TODO 서버에서 받아온 남은 횟수 값 집어넣기
                text = getString(R.string.my_page_level_next_info, "13")
            }
        }
    }

    private fun setBossProfileLayout() {
        // TODO 사용자이름
        binding.tvMyPageProfileName.text = getString(R.string.my_page_boss_name, "나는보스다")
    }

    private fun setTeacherMenuBarLayout() {
        with(binding) {
            tvMyPageMenuBarThird.text = getString(R.string.my_page_teacher_menu_bar_tp)
            ivMyPageMenuBarThird.setImageResource(R.drawable.ic_teacher_point_30)
        }
    }

    private fun setTeacherMenuLayout() {
        with(binding) {
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
    }

    private fun setBossMenuLayout() {
        with(binding) {
            includeMyPageMenuAccountChange.title =
                getString(R.string.my_page_menu_payment_question_ticket)
            includeMyPageMenuExchange.title = getString(R.string.my_page_menu_payment_history)
            includeMyPageMenuExchangeDetails.root.visibility = View.GONE
            includeMyPageMenuTeacherTalkQuestionPost.title =
                getString(R.string.my_page_menu_teacher_talk_question_post)
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

    private fun showTeacherLevelDialogFragment() {
        DialogTeacherLevelFragment().show(parentFragmentManager, TEACHER_LEVEL_DIALOG)
    }

    companion object {
        private const val INQUIRE_WEB_LINK =
            "https://docs.google.com/forms/d/e/1FAIpQLScvoVxh-1jlqyKhVKiFS4pZDhk-GtYbZOHKh4KJHveutN2TYw/viewform"
        private const val TERMS_WEB_LINK =
            "https://beautiful-pharaoh-385.notion.site/3f2236a9632b4edca4b7a0175308f43b?pvs=4"
        private const val LOGOUT_DIALOG = "logoutModal"
        private const val TEACHER_LEVEL_DIALOG = "teacherLevelModal"
        private const val ROLE_TEACHER = "TEACHER"
        private const val ROLE_BOSS = "BOSS"
    }
}
