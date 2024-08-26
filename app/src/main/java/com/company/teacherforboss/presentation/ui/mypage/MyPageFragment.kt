package com.company.teacherforboss.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentMyPageBinding
import com.company.teacherforboss.domain.model.mypage.ChipInfoResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.company.teacherforboss.presentation.ui.common.TeacherProfileActivity
import com.company.teacherforboss.presentation.ui.mypage.boss_talk.MyPageBossTalkWriteActivity
import com.company.teacherforboss.presentation.ui.mypage.community.MyPageTeacherTalkActivity
import com.company.teacherforboss.presentation.ui.mypage.exchange.AccountChangeActivity
import com.company.teacherforboss.presentation.ui.mypage.exchange.ExchangeActivity
import com.company.teacherforboss.presentation.ui.notification.NotificationActivity
import com.company.teacherforboss.presentation.ui.mypage.exchange.ExchangeHistoryActivity
import com.company.teacherforboss.presentation.ui.mypage.modify.ModifyProfileActivity
import com.company.teacherforboss.presentation.ui.mypage.saved.SavedTalkActivity
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_PROFILE_ID
import com.company.teacherforboss.util.component.DialogPopupFragment
import com.company.teacherforboss.util.context.navigateToWebView
import com.company.teacherforboss.util.view.UiState
import com.company.teacherforboss.util.view.loadCircularImage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: MyPageViewModel by activityViewModels()
    private val accountViewModel: ManageAccountViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPageViewModel = viewModel

        getProfile()
        getChipInfo()
        addListeners()
        collectData()
        gotoModifyActivity()
    }

    private fun initLayout(profile: MyPageProfileEntity, chipData: ChipInfoResponseEntity) {
        binding.ivMyPageProfile.loadCircularImage(profile.profileImg)
        viewModel.setProfileImg(profile.profileImg)
        viewModel.setNickname(profile.nickname)
        val role = profile.role
        if (role == ROLE_TEACHER) {
            setTeacherMenuLayout()
            setTeacherMenuBarLayout()
            setTeacherProfileLayoutByAPI(profile)
            setTeacherProfileLayout()
            setTeacherChipInfo(chipData)

        } else {
            setBossMenuLayout()
            setBossProfileLayoutByAPI(profile)
            setBossChipInfo(chipData)
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
            includeMyPageMenuAccount.root.setOnClickListener{
                val intent = Intent(context,ManageAccountActivity::class.java)
                startActivity(intent)
            }
            includeMyPageMenuAccountChange.root.setOnClickListener {
                if (viewModel.getRole() == ROLE_TEACHER) {
                    val intent = Intent(context, AccountChangeActivity::class.java)
                    startActivity(intent)
                } else {
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fcv_teacher_for_boss, AskPaymentFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

            }
            includeMyPageTopAppBar.icNotification.setOnClickListener {
                navigateToAlarm()
            }
            includeMyPageMenuExchange.root.setOnClickListener{
                if (viewModel.getRole() == ROLE_TEACHER) {
                    val intent = Intent(context, ExchangeActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(context,AskPaymentHistoryActivity::class.java)
                    startActivity(intent)
                }
            }

            includeMyPageMenuBossTalkWrittenPost.root.setOnClickListener {
                Intent(context, MyPageBossTalkWriteActivity::class.java).apply {
                    putExtra(ROLE_BOSS, BOSS_TALK_WRITE_POST)
                    startActivity(this)
                }
            }

            includeMyPageMenuBossTalkCommentPost.root.setOnClickListener {
                Intent(context, MyPageBossTalkWriteActivity::class.java).apply {
                    putExtra(ROLE_BOSS, BOSS_TALK_COMMENT_POST)
                    startActivity(this)
                }
            }

            includeMyPageMenuExchangeDetails.root.setOnClickListener{
                val intent = Intent(context,ExchangeHistoryActivity::class.java)
                startActivity(intent)
            }
            includeMyPageMenuTeacherTalkQuestionPost.root.setOnClickListener{
                val intent = Intent(context,MyPageTeacherTalkActivity::class.java)
                intent.putExtra("role",viewModel.getRole())
                startActivity(intent)
            }
            tvLogOutBtn.setOnClickListener { showLogoutDialogFragment() }
            layoutMyPageLevelInfo.setOnClickListener { showTeacherLevelDialogFragment() }

            ivMyPageMenuBarBookmark.setOnClickListener{
                val intent = Intent(context,SavedTalkActivity::class.java)
                startActivity(intent)
            }
            includeMyPageMenuSavedPost.root.setOnClickListener{
                val intent = Intent(context,SavedTalkActivity::class.java)
                startActivity(intent)
            }
            tvMyPageProfileName.setOnClickListener {
                Intent(context,TeacherProfileActivity::class.java).apply {
                    putExtra(TEACHER_PROFILE_ID,viewModel.getMemberId())
                    startActivity(this)
                }
            }
            ivMyPageProfile.setOnClickListener {
                Intent(context,TeacherProfileActivity::class.java).apply {
                    putExtra(TEACHER_PROFILE_ID,viewModel.getMemberId())
                    startActivity(this)
                }
            }
        }
    }

    private fun getProfile() {
        viewModel.getUserProfile()
    }

    private fun getChipInfo() {
        viewModel.getUserChipInfo()
    }
    private fun collectData() {
        viewModel.userProfileInfoState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { userProfileInfoState ->
                when (userProfileInfoState) {
                    is UiState.Success -> {
                        val profileData = userProfileInfoState.data

                        viewModel.userChipInfoState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                            .onEach { chipInfoState ->
                                when (chipInfoState) {
                                    is UiState.Success -> {
                                        val chipData = chipInfoState.data

                                        initLayout(profileData, chipData)
                                    }
                                    else -> Unit
                                }
                            }.launchIn(viewLifecycleOwner.lifecycleScope)

                        viewModel._role.value = profileData.role
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        accountViewModel.logoutState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { logoutState->
                when(logoutState){
                    is UiState.Success->{
                        accountViewModel.clearTokens()
                        gotoLoginActivity()
                    }
                    else->Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setTeacherChipInfo(data: ChipInfoResponseEntity) {
        with(binding) {
            tvMyPageMenuBarFirstCount.text = data.answerCount.toString()
            tvMyPageMenuBarBookmarkCount.text = data.bookmarkCount.toString()
            tvMyPageMenuBarThird.text = getString(
                R.string.my_page_teacher_menu_bar_tp,
                data.points.toString()
            )
        }
    }
    private fun setBossChipInfo(data: ChipInfoResponseEntity) {
        with(binding) {
            tvMyPageMenuBarFirstCount.text = data.questionCount.toString()
            tvMyPageMenuBarBookmarkCount.text = data.bookmarkCount.toString()
            tvMyPageMenuBarThird.text = getString(
                R.string.my_page_boss_menu_bar_question_ticket,
                data.questionTicketCount.toString()
            )
        }
    }

    private fun setTeacherProfileLayout() {
        with(binding) {
            tvMyPageLevel.visibility = View.VISIBLE
            layoutMyPageLevelInfo.visibility = View.VISIBLE
        }
    }

    private fun setTeacherProfileLayoutByAPI(data: MyPageProfileEntity) {
        binding.apply {
            tvMyPageProfileName.text = getString(
                R.string.my_page_teacher_name,
                data.nickname,
            )
            tvMyPageLevel.text = data.teacherInfo!!.level
            tvMyPageLevelInfo.text =
                if (data.teacherInfo!!.leftAnswerCount == 0) {
                    getString(R.string.my_page_level_next_info_null)
                } else {
                    getString(
                        R.string.my_page_level_next_info,
                        data.teacherInfo!!.leftAnswerCount.toString(),
                    )
                }
        }
    }

    private fun setBossProfileLayoutByAPI(data: MyPageProfileEntity) {
        binding.tvMyPageProfileName.text = getString(
            R.string.my_page_boss_name,
            data.nickname,
        )
    }

    private fun setTeacherMenuBarLayout() {
        with(binding) {
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
            clickRightBtn = {accountViewModel.postLogout()},
        ).show(parentFragmentManager, LOGOUT_DIALOG)
    }

    private fun showTeacherLevelDialogFragment() {
        DialogTeacherLevelFragment().show(parentFragmentManager, TEACHER_LEVEL_DIALOG)
    }
    fun gotoLoginActivity(){
        Intent(requireActivity(), LoginActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun gotoModifyActivity() {
        binding.layoutMyPageProfileFix.setOnClickListener {
            if(viewModel.role.value == ROLE_TEACHER) {
                Intent(requireActivity(), ModifyProfileActivity::class.java).apply {
                    putExtra(ROLE, ROLE_TEACHER)
                    putExtra(TEACHER_PROFILE_ID,viewModel.getMemberId())
                    startActivity(this)
                }
            }
            else {
                Intent(requireActivity(), ModifyProfileActivity::class.java).apply {
                    putExtra(ROLE, ROLE_BOSS)
                    putExtra(NICKNAME, viewModel.nickname.value)
                    putExtra(PROFILE_IMG, viewModel.profileImg.value)
                    startActivity(this)
                }
            }
        }
    }

    private fun navigateToAlarm(){
        Intent(requireContext(), NotificationActivity::class.java).apply {
            startActivity(this)
        }
    }

    companion object {
        private const val INQUIRE_WEB_LINK =
            "https://docs.google.com/forms/d/e/1FAIpQLScvoVxh-1jlqyKhVKiFS4pZDhk-GtYbZOHKh4KJHveutN2TYw/viewform"
        private const val TERMS_WEB_LINK =
            "https://beautiful-pharaoh-385.notion.site/3f2236a9632b4edca4b7a0175308f43b?pvs=4"
        private const val LOGOUT_DIALOG = "logoutModal"
        const val TEACHER_LEVEL_DIALOG = "teacherLevelModal"
        private const val ROLE = "ROLE"
        private const val ROLE_TEACHER = "TEACHER"
        private const val ROLE_BOSS = "BOSS"
        private const val NICKNAME = "nickname"
        private const val PROFILE_IMG = "profileImg"
        private const val BOSS_TALK_WRITE_POST = "bossTalkWritePost"
        private const val BOSS_TALK_COMMENT_POST = "bossTalkCommentPost"
    }
}
